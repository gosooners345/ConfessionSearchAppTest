package com.confessionsearchapptest.release1.ui.NotesActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.confessionsearchapptest.release1.MainActivity
import com.confessionsearchapptest.release1.R
import com.confessionsearchapptest.release1.data.notes.NoteRepository
import com.confessionsearchapptest.release1.data.notes.Notes
import com.confessionsearchapptest.release1.databinding.FragmentNotesBinding
import com.confessionsearchapptest.release1.helpers.NotesAdapter
import com.confessionsearchapptest.release1.helpers.OnNoteListener
import com.confessionsearchapptest.release1.helpers.RecyclerViewSpaceExtender
import java.util.*

class NotesFragment : Fragment(), OnNoteListener {

    private lateinit var notesViewModel: NotesViewModel
    private var _binding: FragmentNotesBinding? = null
    var notesList: RecyclerView? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        notesViewModel =
            ViewModelProvider(this).get(NotesViewModel::class.java)
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        notesList = root.findViewById(R.id.notesListView)
         notesViewModel.noteRepository = NoteRepository(context)
         fetchNotes()
      //  Collections.sort(notesArrayList,Notes.compareDateTime)
         notesList = root.findViewById(R.id.notesListView)
         adapter = NotesAdapter(notesArrayList, this, requireContext())
         notesList!!.layoutManager = LinearLayoutManager(context)
         notesList!!.itemAnimator = DefaultItemAnimator()
         notesList!!.adapter = adapter
         val divider = RecyclerViewSpaceExtender(8)
         notesList!!.addItemDecoration(divider)

         ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(notesList)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Load notes
    override fun onNoteClick(position: Int) {

        notesArrayList[position]
        val intent = Intent(context, NotesComposeActivity::class.java)
        intent.putExtra("activity_ID", ACTIVITY_ID)
        intent.putExtra("note_selected", notesArrayList[position])
        startActivity(intent)

    }

    //Implement delete functionality
    var itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                notesViewModel.deleteNote(notesArrayList[viewHolder.bindingAdapterPosition])


            }
        }

    //Critical for retrieving notes for the application
    private fun fetchNotes() {

        notesViewModel.noteRepository!!.fetchNotes().observe(viewLifecycleOwner, { notes ->
            if (notesArrayList.size > 0) notesArrayList.clear()
            if (notes != null) {
                notesArrayList.addAll(notes)
            }
            Collections.sort(notesArrayList,Notes.compareDateTime)
            adapter!!.notifyDataSetChanged()
        }

        )

    }


    companion object {
        @JvmField
        var adapter: NotesAdapter? = null
        var notesArrayList = ArrayList<Notes>()
        const val ACTIVITY_ID = 32
        const val buttonText = "New Note"
        const val buttonPic = R.drawable.ic_add_note
        fun NewNote(context: Context?) {
            val intent = Intent(context, NotesComposeActivity::class.java)
            intent.putExtra("activity_ID", ACTIVITY_ID)
            context!!.startActivity(intent)
        }
    }
}
