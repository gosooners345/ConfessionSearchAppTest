package com.confessionsearchapptest.release1.data.notes

import android.os.AsyncTask
import com.confessionsearchapptest.release1.async.CoroutinesAsyncTask

class UpdateAsync(private val mNotesDao: NoteDao) : CoroutinesAsyncTask<Notes?, Void?, Void?>("update") {

     override fun doInBackground(vararg notes: Notes?): Void? {
        mNotesDao.updateNotes(*notes)
        return null
    }
}