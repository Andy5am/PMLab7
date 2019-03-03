package com.example.lab7.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData


class ContactRepository(application: Application) {

    private var contactDao:ContactDao

    private var allContacts: LiveData<List<Contact>>

    init {
        val database: ContactDatabase = ContactDatabase.getInstance(
            application.applicationContext
        )!!
        contactDao = database.contactDao()
        allContacts = contactDao.getAllContacts()
    }

    fun insert(contact: Contact) {
        val insertContactAsyncTask = InsertContactAsyncTask(contactDao).execute(contact)
    }

    fun update(contact: Contact) {
        val updateContactAsyncTask = UpdateContactAsyncTask(contactDao).execute(contact)
    }

    fun delete(contact: Contact) {
        val deleteNoteAsyncTask = DeleteContactAsyncTask(contactDao).execute(contact)
    }

    fun deleteAllNotes() {
        val deleteAllNotesAsyncTask = DeleteAllContactAsyncTask(
            contactDao
        ).execute()
    }

    fun getAllNotes(): LiveData<List<Contact>> {
        return allContacts
    }

    companion object {
        private class InsertContactAsyncTask(contactDao: ContactDao) : AsyncTask<Contact,Unit,Unit>() {
            val contactDao = contactDao

            override fun doInBackground(vararg params: Contact?) {
                contactDao.insert(params[0]!!)
            }
        }

        private class UpdateContactAsyncTask(contactDao: ContactDao) : AsyncTask<Contact, Unit, Unit>() {
            val contactDao = contactDao

            override fun doInBackground(vararg params: Contact?) {
                contactDao.update(params[0]!!)
            }
        }

        private class DeleteContactAsyncTask(contactDao: ContactDao) : AsyncTask<Contact,Unit,Unit>() {
            val contactDao = contactDao

            override fun doInBackground(vararg params: Contact?) {
                contactDao.delete(params[0]!!)
            }
        }

        private class DeleteAllContactAsyncTask(contactDao: ContactDao) : AsyncTask<Unit,Unit,Unit>() {
            val contactDao = contactDao

            override fun doInBackground(vararg params: Unit?) {
                contactDao.deleteAllContacts()
            }
        }
    }
}