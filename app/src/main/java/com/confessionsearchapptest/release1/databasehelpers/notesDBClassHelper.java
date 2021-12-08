package com.confessionsearchapptest.release1.databasehelpers;

import android.content.Context;

import androidx.room.*;
import androidx.room.RoomDatabase;

import com.confessionsearchapptest.release1.data.notes.NoteDao;
import com.confessionsearchapptest.release1.data.notes.Notes;
//DatabaseLine
@Database(entities = {Notes.class},version = 3,autoMigrations ={ @AutoMigration(from=2,to=3)},exportSchema = true )
public abstract class notesDBClassHelper extends RoomDatabase {
    public static final String DATABASE_NAME = "confessionsearchNotes_db";
    private static notesDBClassHelper instance;

    public static notesDBClassHelper getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), notesDBClassHelper.class, DATABASE_NAME).build();
            return instance;
        }
        return instance;
    }

    public abstract NoteDao getNoteDao();
}