package com.example.ejemplo26feb.data

import android.app.Application

class NoteRepository(application: Application){

    private var noteDao: NoteDao

    private ar allNotes: LiveData

    init{
        val database: NoteDatabase.getInstance(
        application.applicationContext
        )!
        noteDao = database.noteDao()
        allNotes = noteDao.getAllNotes()
    }

    fun insert(note: Note){
        InsertAsyncTask(noteDao).execute(note)
    }
    fun update(note:Note){
        UpdateAsyncTask(noteDao).execute(note)
    }
}