package com.confessionsearchapptest.release1.ui.bible

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ShareActionProvider
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.confessionsearchapptest.release1.R

class BibleViewerFragment : Fragment() {
private val bibleReaderTitle : String? = null
    var action: ShareActionProvider? = null
    var shareNote : String? = null
    var shareList = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val verseText = requireArguments().getString(VERSE,"")
        val bookName = requireArguments().getString(BOOKNAME,"")
        val verseNum = requireArguments().getInt(verseNum,0)
        val chapterNumber = requireArguments().getInt(CHAPTERNUM,0)
        val view = inflater.inflate(R.layout.fragment_bible_view_results,container,false)
    val chTextBox = view.findViewById<TextView>(R.id.chapterText)
        val chHeader = view.findViewById<TextView>(R.id.chapterHeader)
        if(verseNum>0)
         chHeader.text = bookName+ " " + chapterNumber + ":"+verseNum
        else
            chHeader.text = bookName+" "+chapterNumber
        chTextBox.text=verseText
        return view
    //return super.onCreateView(inflater, container, savedInstanceState)
    }
    companion object{
        private const val CHAPTERNUM="chapter"
        private const val VERSE = "verse"
        private const val BOOKNAME = "book"
        private const val verseNum = "verseNum"


        fun NewVerse(ChapterNum:Int?,Verse:String?,VerseNum:Int?,BookName:String?):BibleViewerFragment{
            val fragment = BibleViewerFragment()
            val spaces = Bundle()
            spaces.putInt(CHAPTERNUM,ChapterNum!!)
            spaces.putInt(verseNum,VerseNum!!)
            spaces.putString(BOOKNAME,BookName!!)
            spaces.putString(VERSE,Verse!!)
            fragment.arguments=spaces
            return fragment
        }
    }

}