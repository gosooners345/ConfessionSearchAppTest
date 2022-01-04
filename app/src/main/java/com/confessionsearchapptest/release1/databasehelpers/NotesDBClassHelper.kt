package com.confessionsearchapptest.release1.databasehelpers

import android.content.Context
import androidx.room.Database
import androidx.room.AutoMigration
import androidx.room.RoomDatabase
import com.confessionsearchapptest.release1.data.notes.NoteDao
import androidx.room.Room
import com.confessionsearchapptest.release1.data.notes.Notes

//DatabaseLine
@Database(
    entities = [Notes::class],
    version =4,
    autoMigrations = [AutoMigration(from = 2, to = 3),AutoMigration(from =3, to =4)],
    exportSchema = true
)
abstract class NotesDBClassHelper : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao?

    companion object {
        const val DATABASE_NAME = "confessionsearchNotes_db"
        private var instance: NotesDBClassHelper? = null
        fun getInstance(context: Context): NotesDBClassHelper? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDBClassHelper::class.java, DATABASE_NAME
                ).build()
                return instance
            }
            return instance
        }
    }
}