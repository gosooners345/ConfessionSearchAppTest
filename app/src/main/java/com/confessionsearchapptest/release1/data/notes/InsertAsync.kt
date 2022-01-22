package com.confessionsearchapptest.release1.data.notes

import android.os.AsyncTask
import com.confessionsearchapptest.release1.async.CoroutinesAsyncTask

class InsertAsync(private val mNotesDao: NoteDao) : CoroutinesAsyncTask<Notes?, Void?, Void?>("insert") {
    override fun doInBackground(vararg notes: Notes?): Void? {
        mNotesDao.insertNotes(*notes)
        return null
    }
}