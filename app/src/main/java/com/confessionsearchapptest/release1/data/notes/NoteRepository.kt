package com.confessionsearchapptest.release1.data.notes

import android.content.Context
import androidx.lifecycle.LiveData
import com.confessionsearchapptest.release1.databasehelpers.NotesDBClassHelper


class NoteRepository(context: Context?) {

    private var notesDB: NotesDBClassHelper? = null

    init {
        notesDB = NotesDBClassHelper.getInstance(context!!)
    }

    fun insertNote(note: Notes?) {
        InsertAsync(notesDB!!.getNoteDao()!!).execute(note)
    }

    fun updateNote(note: Notes?) {
        UpdateAsync(notesDB!!.getNoteDao()!!).execute(note)
    }

    fun deleteNote(note: Notes?) {
        DeleteAsync(notesDB!!.getNoteDao()!!).execute(note)
    }


    fun fetchNotes(): LiveData<List<Notes>> {
        return notesDB!!.getNoteDao()!!.fetchNotes()
    }
}
