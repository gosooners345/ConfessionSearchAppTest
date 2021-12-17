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
    version = 3,
    autoMigrations = [AutoMigration(from = 2, to = 3)],
    exportSchema = true
)
abstract class notesDBClassHelper : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao?

    companion object {
        const val DATABASE_NAME = "confessionsearchNotes_db"
        private var instance: notesDBClassHelper? = null
        fun getInstance(context: Context): notesDBClassHelper? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    notesDBClassHelper::class.java, DATABASE_NAME
                ).build()
                return instance
            }
            return instance
        }
    }
}