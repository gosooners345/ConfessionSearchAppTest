package com.confessionsearchapptest.release1.ui.NotesActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.confessionsearchapptest.release1.MainActivity
import com.confessionsearchapptest.release1.data.notes.NoteRepository
import com.confessionsearchapptest.release1.data.notes.Notes
import com.confessionsearchapptest.release1.ui.NotesActivity.NotesFragment.Companion.adapter
import java.util.*

class NotesViewModel : ViewModel() {
    var noteRepository: NoteRepository? = null


    //Delete notes from database
    fun deleteNote(note: Notes) {
        val notePosition = NotesFragment.notesArrayList.indexOf(note)
        NotesFragment.notesArrayList.remove(note)
        noteRepository!!.deleteNote(note)
        NotesFragment.adapter!!.notifyItemRemoved(notePosition)
Collections.sort(NotesFragment.notesArrayList,Notes.compareDateTime)
    }

    //Load Notes from Database


}