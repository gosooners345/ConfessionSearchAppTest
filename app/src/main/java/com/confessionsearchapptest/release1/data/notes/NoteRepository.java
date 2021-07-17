package com.confessionsearchapptest.release1.data.notes;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;

import com.confessionsearchapptest.release1.databasehelpers.notesDBClassHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class NoteRepository {

    private notesDBClassHelper notesDB;

    public NoteRepository(Context context)
    {
        notesDB= notesDBClassHelper.getInstance(context);
    }
    public void insertNote(Notes note){
new InsertAsync(notesDB.getNoteDao()).execute(note);
    }
    public void updateNote(Notes note){
        new UpdateAsync(notesDB.getNoteDao()).execute(note);
    }

    public void deleteNote(Notes note){
        new DeleteAsync(notesDB.getNoteDao()).execute(note);
    }
   // public ArrayList<Notes> fetchNotes2(){return notesDB.getNoteDao().getNotes();}

    public LiveData<List<Notes>>fetchNotes(){
        return notesDB.getNoteDao().fetchNotes();
    }
}
