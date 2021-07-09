package com.confessionsearchapptest.release1.data.notes;

import android.os.AsyncTask;

public class DeleteAsync extends AsyncTask<Notes, Void, Void> {

    private NoteDao mNoteDao;

    public DeleteAsync(NoteDao dao) {
        mNoteDao = dao;
    }

    @Override
    protected Void doInBackground(Notes... notes) {
        mNoteDao.delete(notes);
        return null;
    }
    }
