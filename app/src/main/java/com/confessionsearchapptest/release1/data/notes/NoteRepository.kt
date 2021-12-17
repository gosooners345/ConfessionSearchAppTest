package com.confessionsearchapptest.release1.data.notes

import android.content.Context
import androidx.lifecycle.LiveData
import com.confessionsearchapptest.release1.databasehelpers.notesDBClassHelper


class NoteRepository {

    private var notesDB: notesDBClassHelper? = null

    constructor(context: Context?) {
        notesDB = notesDBClassHelper.getInstance(context!!)
    }

    fun insertNote(note: Notes?) {
        InsertAsync(notesDB!!.getNoteDao()).execute(note)
    }

    fun updateNote(note: Notes?) {
        UpdateAsync(notesDB!!.getNoteDao()!!).execute(note)
    }

    fun deleteNote(note: Notes?) {
        DeleteAsync(notesDB!!.getNoteDao()).execute(note)
    }


    fun fetchNotes(): LiveData<List<Notes?>?>? {
        return notesDB!!.getNoteDao()!!.fetchNotes()
    }
}
