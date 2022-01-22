package com.confessionsearchapptest.release1.data.notes

import android.os.AsyncTask
import com.confessionsearchapptest.release1.async.CoroutinesAsyncTask

class DeleteAsync(private val mNoteDao: NoteDao) : CoroutinesAsyncTask<Notes?, Void?, Void?>("delete") {
     override fun doInBackground(vararg notes: Notes?): Void? {
        mNoteDao.delete(*notes)
        return null
    }
}