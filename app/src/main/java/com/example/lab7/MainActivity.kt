package com.example.lab7

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab7.data.Contact
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_CONTACT_REQUEST = 1
        const val EDIT_CONTACT_REQUEST = 2
    }

    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAddNote.setOnClickListener {
            startActivityForResult(
                Intent(this, EditContactActivity::class.java),
                ADD_CONTACT_REQUEST
            )
        }

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        var adapter = ContactAdapter()

        recycler_view.adapter = adapter

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)

        contactViewModel.getAllContacts().observe(this, Observer<List<Contact>> {
            adapter.submitList(it)
        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                contactViewModel.delete(adapter.getContactAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Note Deleted!", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object : ContactAdapter.OnItemClickListener {
            override fun onItemClick(contact: Contact) {
                var intent = Intent(baseContext, EditContactActivity::class.java)
                intent.putExtra(EditContactActivity.EXTRA_ID, contact.id)
                intent.putExtra(EditContactActivity.EXTRA_NAME, contact.name)
                intent.putExtra(EditContactActivity.EXTRA_PHONE, contact.phone)
                intent.putExtra(EditContactActivity.EXTRA_MAIL,contact.mail)
                intent.putExtra(EditContactActivity.EXTRA_PRIORITY, contact.priority)

                startActivityForResult(intent, EDIT_CONTACT_REQUEST)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_contacts -> {
                contactViewModel.deleteAllContacts()
                Toast.makeText(this, "All notes deleted!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            val newNote = Contact(
                data!!.getStringExtra(EditContactActivity.EXTRA_NAME),
                data.getStringExtra(EditContactActivity.EXTRA_PHONE),
                data.getStringExtra(EditContactActivity.EXTRA_MAIL),
                data.getIntExtra(EditContactActivity.EXTRA_PRIORITY, 1)
            )
            contactViewModel.insert(newNote)

            Toast.makeText(this, "Contacto guardado", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(EditContactActivity.EXTRA_ID, -1)

            if (id == -1) {
                Toast.makeText(this, "No se pudo actualizar", Toast.LENGTH_SHORT).show()
            }

            val updateNote = Contact(
                data!!.getStringExtra(EditContactActivity.EXTRA_NAME),
                data.getStringExtra(EditContactActivity.EXTRA_PHONE),
                data.getStringExtra(EditContactActivity.EXTRA_MAIL),
                data.getIntExtra(EditContactActivity.EXTRA_PRIORITY, 1)
            )
            updateNote.id = data.getIntExtra(EditContactActivity.EXTRA_ID, -1)
            contactViewModel.update(updateNote)

        } else {
            Toast.makeText(this, "Contacto no guardado", Toast.LENGTH_SHORT).show()
        }


    }
}


