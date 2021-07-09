package com.confessionsearchapptest.release1.databasehelpers;

import android.content.Context;

import androidx.room.*;
import androidx.room.RoomDatabase;

@Database(entities = {Notes.class},version = 1)
public abstract class notesDBClassHelper extends RoomDatabase {
    public static final String DATABASE_NAME = "confessionsearchNotes_db";
    private static notesDBClassHelper instance;

    static notesDBClassHelper getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), notesDBClassHelper.class, DATABASE_NAME).build();
            return instance;
        }
        return instance;
    }

    public abstract NoteDao getNoteDao();
}





