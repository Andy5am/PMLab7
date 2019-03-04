package com.example.ejemplo26feb.data

import android.content.Context
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase(){

    abstract fun noteDao():NoteDao

    companion object {
        private var instance:NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase?{
            if (instance == null)
            synchronized(NoteDatabase::class){
                instance = Room.databaseBuilder(
                    context.applicationContext,NoteDatabase::class.java,"note_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }
            return instance
        }

        fun destroyInstance(){
            instance = null
        }
    }

    private val roomCallback = object :RoomDatabase.Callback(){
        override fun onCreate(db:SupportSQLiteDatabase){
            super.onCreate(db)
            PopulateDbAryncTask()
        }
    }

    class PopulateDAsyncTass(db:NoteDatabase?):AsyncTask<Unit,Unit,Unit>{
        private val noteDao = db?.noteDao()
        override fun doInBackground(vararg p0: Unit?){
            noteDao?.insert(Note("title 1","description 1",1))
            noteDao?.insert(Note("title 2","description 2",2))
            noteDao?.insert(Note("title 3","descripiton 3",3))
        }
    }
}