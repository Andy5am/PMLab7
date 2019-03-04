package com.example.lab7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_see_contact.*

class SeeContactActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "com.example.lab7.EXTRA_ID"
        const val EXTRA_NAME = "com.example.lab7.EXTRA_NAME"
        const val EXTRA_PHONE = "com.example.lab7.EXTRA_PHONE"
        const val EXTRA_MAIL = "com.example.lab7.EXTRA_MAIL"
        const val EXTRA_PIC = "com.example.lab7.EXTRA_PIC"
        const val EXTRA_PRIORITY = "com.example.lab7.EXTRA_PRIORITY"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_contact)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_edit)

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Contact"
            see_name.text = intent.getStringExtra(EXTRA_NAME)
            see_phone.text = intent.getStringExtra(EXTRA_PHONE)
            see_mail.text = intent.getStringExtra(EXTRA_MAIL)
            see_priority.setText(intent.getIntExtra(EXTRA_PRIORITY,1))
        }else{
            title = " Edit Contact"
        }
    }
}
