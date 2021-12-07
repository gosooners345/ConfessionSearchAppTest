
package com.confessionsearchapptest.release1.ui.NotesActivity

import android.app.Dialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import www.sanju.motiontoast.MotionToast
import com.google.android.material.textfield.TextInputLayout
import com.confessionsearchapptest.release1.R
import com.confessionsearchapptest.release1.data.notes.NoteRepository
import com.confessionsearchapptest.release1.data.notes.Notes
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton


class NotesComposeActivity : AppCompatActivity() {
    var notesSubject: TextInputLayout? = null
    var notesContent: TextInputLayout? = null
    var activityID = 0
    var saveButton: Button? = null
    var editButton: Button? = null
    var isNewNote = false
    var noteContentString = ""
    var noteSubjectString = ""
    var shareList = ""
    var newNote: Notes? = null
    var incomingNote: Notes? = null
    var noteRepository: NoteRepository? = null
    var mode = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.notes_compose_layout)
        notesContent = findViewById(R.id.contentContainer)
        notesSubject = findViewById(R.id.topicContainer)
        noteRepository = NoteRepository(this)

        //Load Notes
        if (!intentInfo) {
            notesSubject!!.editText!!.setText(newNote!!.name)
            notesContent!!.editText!!.setText(newNote!!.content)
        } else {
            notesSubject!!.editText!!.setText("")
            notesContent!!.editText!!.setText("")
        }
        notesSubject!!.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, after: Int) {
                noteSubjectString = s.toString()
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        notesContent!!.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                noteContentString = s.toString()
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        saveButton = findViewById(R.id.saveNote)
        saveButton!!.setOnClickListener(SaveNote)
        editButton = findViewById(R.id.editButton)
        activityID = intent.getIntExtra("activity_ID", -1)
        editButton!!.setOnClickListener(editNote)
    }

    //Enable or disable Editing
    var editNote = View.OnClickListener {
        when (mode) {
            EDIT_OFF -> EnableEdit()
            EDIT_ON -> DisableEdit()
        }
    }

    //Save Note to device
    var SaveNote: View.OnClickListener = object : View.OnClickListener {
        @RequiresApi(Build.VERSION_CODES.N)
        override fun onClick(view: View) {
            noteSubjectString = notesSubject!!.editText!!.text.toString()
            noteContentString = notesContent!!.editText!!.text.toString()
            //Update or save new content to note
            newNote = if (!isNewNote) Notes(
                noteSubjectString,
                noteContentString,
                incomingNote!!.noteID
            )
            else Notes()
            newNote!!.name = noteSubjectString
            newNote!!.content = noteContentString
            newNote!!.timeModified=System.currentTimeMillis()

            run {
                //Update or insert new note into database
                if (isNewNote) {
                    noteRepository!!.insertNote(newNote)
                } else noteRepository!!.updateNote(newNote)
                if (activityID == 32)
                {

                    NotesFragment.adapter!!.notifyDataSetChanged()
                }
                Log.i(TAG, "Saving note to storage")
            }
            //Close this activity out and head back to parent screen
            finish()
        }
    }

    //find out if the note is new or old
    private val intentInfo: Boolean
        private get() {
            if (intent.hasExtra("note_selected")) {
                incomingNote = intent.getParcelableExtra("note_selected")
                newNote = Notes()
                newNote!!.noteID = incomingNote!!.noteID
                newNote!!.content = incomingNote!!.content
                newNote!!.name = incomingNote!!.name

                mode = EDIT_ON
                isNewNote = false
                return false
            } else if (intent.hasExtra("search_result_save")) {
                val contentString = intent.getStringExtra("search_result_save")
                newNote = Notes()
                newNote!!.content = contentString

                mode = EDIT_ON
                isNewNote = true
                return false
            }
            mode = EDIT_ON
            newNote = Notes()
            isNewNote = true
            return true
        }

    //back button
    override fun onBackPressed() {
        if (mode == EDIT_OFF) {
            var darkMode = false
            when (applicationContext.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> darkMode = true
                Configuration.UI_MODE_NIGHT_NO -> darkMode = false

            }
            val alert = AlertDialog.Builder(this)
                .setTitle("Save Note?")
                .setMessage(String.format(resources.getString(R.string.save_note_message)))
                .setNegativeButton("No") { dialog, which -> finish() }
                .setPositiveButton("Yes") { dialog, which -> saveButton!!.performClick() }
            val dialog: Dialog = alert.create()
            if (!isFinishing) dialog.show()

        } else {
            DisableEdit()

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("mode", mode)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        mode = savedInstanceState.getInt("mode")
        if (mode == EDIT_ON) {
            when (applicationContext.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    MotionToast.darkToast(
                        this,
                        "Edit Mode On",
                        "Note Editing Enabled!",
                        MotionToast.TOAST_INFO,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.SHORT_DURATION,
                        ResourcesCompat.getFont(this, R.font.helvetica_regular)
                    )
                }
                Configuration.UI_MODE_NIGHT_NO -> {
                    MotionToast.createToast(
                        this,
                        "Edit Mode On",
                        "Note Editing Enabled!",
                        MotionToast.TOAST_INFO,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.SHORT_DURATION,
                        ResourcesCompat.getFont(this, R.font.helvetica_regular)
                    )

                }
            }
        }
    }

    private fun DisableEdit() {
        notesContent!!.isEnabled = false
        notesSubject!!.isEnabled = false
        mode = EDIT_OFF
        when (applicationContext.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                MotionToast.darkToast(
                    this,
                    "Edit Mode Off",
                    "Note Editing Disabled!",
                    MotionToast.TOAST_INFO,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.SHORT_DURATION,
                    ResourcesCompat.getFont(this, R.font.helvetica_regular)
                )
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                MotionToast.createToast(
                    this,
                    "Edit Mode Off",
                    "Note Editing Disabled!",
                    MotionToast.TOAST_INFO,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.SHORT_DURATION,
                    ResourcesCompat.getFont(this, R.font.helvetica_regular)
                )

            }
        }
    }

    private fun EnableEdit() {
        notesContent!!.isEnabled = true
        notesSubject!!.isEnabled = true
        mode = EDIT_ON
        when (applicationContext.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                MotionToast.darkToast(
                    this,
                    "Edit Mode On",
                    "Note Editing Enabled!",
                    MotionToast.TOAST_INFO,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.SHORT_DURATION,
                    ResourcesCompat.getFont(this, R.font.helvetica_regular)
                )
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                MotionToast.createToast(
                    this,
                    "Edit Mode On",
                    "Note Editing Enabled!",
                    MotionToast.TOAST_INFO,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.SHORT_DURATION,
                    ResourcesCompat.getFont(this, R.font.helvetica_regular)
                )

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.share_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareAction -> {
                shareList = """
                ${notesSubject!!.editText!!.text}
                ${notesContent!!.editText!!.text}
                """.trimIndent()
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                val INTENTNAME = "SHARE"
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareList)
                sendIntent.type = "text/plain"
                startActivity(Intent.createChooser(sendIntent, INTENTNAME))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val TAG = "NotesComposeActivity"
        private const val EDIT_ON = 1
        private const val EDIT_OFF = 0
    }
}
