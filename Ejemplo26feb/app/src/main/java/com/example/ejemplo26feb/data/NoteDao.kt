package com.example.ejemplo26feb.data

import android.arch.lifecycle.LiveData
import android.os.AsyncTask

interface NoteDao{
    @Insert
    fun insert(note: Note)

    @Update
    fun update(note:Note)

    @Delete
    fun delete(note:Note)

    @Query("DELETE FROM note_table")
    fun deleteAllNotes()

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getAllNotes(): LiveData<List<Note>>

    companion object {
        private class InsertNoteAsyncTask(noteDao: NoteDao):AsyncTask<Note,Unit,Unit>(){
            val noteDao = noteDao

            override fun doInBackgroud(vararg p0:Note?){
                noteDao.insert(p0[0]!!)
            }
        }

        private class UpdateNoteAsyncTask(noteDao: NoteDao):AsyncTask<Note,Unit,Unit>(){
            val noteDao = noteDao

            override fun doInBackgroud(vararg p0:Note?){
                noteDao.update(p0[0]!!)
            }
        }
        private class DeleteNoteAsyncTask(noteDao: NoteDao):AsyncTask<Note,Unit,Unit>(){
            val noteDao = noteDao
            override fun doInBackground(vararg params: Note?) {
                noteDao.delete(p0[0]!!)
            }
        }
        private class DeleteAllNoteAsyncTask(noteDao: NoteDao):AsyncTask<Unit,Unit,Unit>(){
            val noteDao = noteDao

            override fun doInBackgroud(vararg p0:Unit?){
                noteDao.deleteAllNotes()
            }
        }
    }
}