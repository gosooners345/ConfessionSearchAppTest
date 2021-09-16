package com.confessionsearchapptest.release1.ui.NotesActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.confessionsearchapptest.release1.MainActivity
import com.confessionsearchapptest.release1.data.notes.NoteRepository
import com.confessionsearchapptest.release1.data.notes.Notes
import com.confessionsearchapptest.release1.ui.NotesActivity.NotesFragment.Companion.adapter

class NotesViewModel : ViewModel() {
    var noteRepository: NoteRepository? = null


    //Delete notes from database
    fun deleteNote(note: Notes) {
        MainActivity.notesArrayList.remove(note)
        noteRepository!!.deleteNote(note)
        NotesFragment.adapter!!.notifyDataSetChanged()
    }

    //Load Notes from Database


}