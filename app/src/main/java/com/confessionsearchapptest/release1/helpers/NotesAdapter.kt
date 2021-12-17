package com.confessionsearchapptest.release1.helpers


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.confessionsearchapptest.release1.R
import com.confessionsearchapptest.release1.data.notes.Notes
import kotlin.collections.ArrayList

class NotesAdapter @SuppressLint("NotifyDataSetChanged") constructor(
    private val noteList: ArrayList<Notes>,
    private val onNoteListener: OnNoteListener,
    private val context: Context
) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    private var lastPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val noteView = inflater.inflate(R.layout.notes_item_layout, parent, false)
        return ViewHolder(noteView, onNoteListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = noteList[position]
        val noteTitle = holder.subjectView
        noteTitle.text = note.title
        val contentHolder = holder.contentView
        contentHolder.text = note.content
        val timeStamp = holder.timeStamp
        timeStamp.text = note.time
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    // Adds animation to the cards

    class ViewHolder(itemView: View, private var onNoteListener: OnNoteListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var contentView: TextView = itemView.findViewById(R.id.content_text)
        var subjectView: TextView = itemView.findViewById(R.id.content_Title)
        var timeStamp: TextView = itemView.findViewById(R.id.timeStamp)
        override fun onClick(view: View) {
            onNoteListener.onNoteClick(bindingAdapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    init {
        notifyDataSetChanged()
    }

    companion object {
        @SuppressLint("NeverUsed")
        private fun setAnimation(notesAdapter: NotesAdapter, toAnimate: View, position: Int) {
            if (position > notesAdapter.lastPosition || position < notesAdapter.lastPosition) {
                val animation = AnimationUtils.loadAnimation(
                    notesAdapter.context, R.anim.animate_card_enter
                )
                animation.scaleCurrentDuration(.5f)
                toAnimate.clearAnimation()
                toAnimate.startAnimation(animation)
                notesAdapter.lastPosition = position
            }
        }
    }
}