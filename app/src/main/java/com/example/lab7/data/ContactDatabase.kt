package com.example.lab7.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Contact::class],version = 1)
abstract class ContactDatabase: RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {
        private var instance: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase? {
            if (instance == null) {
                synchronized(ContactDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java, "note_database"
                    )
                        .fallbackToDestructiveMigration() // when version increments, it migrates (deletes db and creates new) - else it crashes
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }

    class PopulateDbAsyncTask(db: ContactDatabase?) : AsyncTask<Unit, Unit, Unit>(){
        private val contactDao = db?.contactDao()

        override fun doInBackground(vararg p0: Unit?) {
            contactDao?.insert(Contact("Pepito","5123467","pepito@gmail.com",1))
            contactDao?.insert(Contact("Pepita","7643215","pepita@gmail.com",2))
            contactDao?.insert(Contact("Juanito","555555","juanitoelbonito@gmail.com",3))
        }
    }

}