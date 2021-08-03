package com.confessionsearchapptest.release1.ui.NotesActivity

import android.content.Intent
import android.os.Bundle
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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class NotesFragment : Fragment(),NotesAdapter.OnNoteListener {

    private lateinit var notesViewModel: NotesViewModel
    private var _binding: FragmentNotesBinding? = null

    var notesList: RecyclerView? = null
    var fab: ExtendedFloatingActionButton? = null


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
        notesViewModel.noteRepository = NoteRepository(context)
        fetchNotes()
        notesList = root.findViewById(R.id.notesListView)
        adapter = NotesAdapter(MainActivity.notesArrayList, this)
        notesList!!.layoutManager = LinearLayoutManager(context)
        notesList!!.itemAnimator = DefaultItemAnimator()
        notesList!!.adapter = adapter
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(notesList)
        fab = root.findViewById(R.id.newNote)
        fab!!.setOnClickListener(newNoteListener)

         return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onNoteClick(position: Int) {

  MainActivity.notesArrayList[position]
        val title = MainActivity.notesArrayList[position].name
        val content = MainActivity.notesArrayList[position].content
        val intent = Intent(context, NotesComposeActivity::class.java)
        intent.putExtra("activity_ID", ACTIVITY_ID)
        intent.putExtra("note_selected", MainActivity.notesArrayList[position])
        startActivity(intent)

    }

    var itemTouchHelperCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
          notesViewModel.deleteNote(MainActivity.notesArrayList[viewHolder.adapterPosition])
        }
    }
    private fun fetchNotes() {

        notesViewModel.noteRepository!!.fetchNotes().observe(viewLifecycleOwner, { notes ->
            if (MainActivity.notesArrayList.size > 0) MainActivity.notesArrayList.clear()
            if (notes != null) {
                MainActivity.notesArrayList.addAll(notes)
            }
            adapter!!.notifyDataSetChanged()
        }
        )

    }


var newNoteListener = View.OnClickListener{
    val intent = Intent(context, NotesComposeActivity::class.java)
        intent.putExtra("activity_ID", ACTIVITY_ID)
        startActivity(intent)
}



    companion object{
        @JvmField
        var adapter: NotesAdapter? = null
        const val ACTIVITY_ID = 32
    }
}
