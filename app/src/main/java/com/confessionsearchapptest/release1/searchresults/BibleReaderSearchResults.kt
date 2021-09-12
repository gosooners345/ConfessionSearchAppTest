package com.confessionsearchapptest.release1.searchresults

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.confessionsearchapptest.release1.data.bible.BibleContentsList
import com.confessionsearchapptest.release1.data.documents.documentDBClassHelper
import com.confessionsearchapptest.release1.R
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.TextView
import com.example.awesomedialog.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.confessionsearchapptest.release1.ui.bible.BibleViewerFragment
import www.sanju.motiontoast.MotionToast


class BibleReaderSearchResults : AppCompatActivity() {
//var bibleVP : ViewPager?=null
  //  var adapter: SearchAdapter? = null
    var shareList: String? = null

    var bibleVerseList = BibleContentsList()
    var docDBhelper: documentDBClassHelper? = null
    var documentDB : SQLiteDatabase? = null
    var header = ""
var bibleReaderHandler :  BibleReaderHandler? = null
  
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
val bibleBookName = intent.getStringExtra("BookName")
val bibleChapterNum = intent.getIntExtra("Chapter",0)
val bibleVerseNum = intent.getIntExtra("VerseNum",0)
val translationName = intent.getStringExtra("Translation")

 bibleReader(bibleBookName,bibleChapterNum,bibleVerseNum,translationName)
    }
   // Where
    fun bibleReader( bibleBook : String?, bibleCh : Int?, bibleVerseNum : Int? , bibleTranslation : String?) {
       Log.d("BibleReader", "Debut")
       docDBhelper = documentDBClassHelper(this)
       documentDB = docDBhelper!!.readableDatabase
       try {
bibleReaderHandler= BibleReaderHandler()
           bibleVerseList = docDBhelper!!.getChaptersandVerses(
               documentDB!!,
               bibleTranslation,
               bibleBook,
               bibleCh,
               bibleVerseNum
           )


           if (bibleCh != 0) {
               var verse = ""
               var verseNum = 0
               if (bibleVerseNum != 0)
               {verse = bibleVerseList[0].VerseText!!

               }
               else {
                   for (verses in bibleVerseList)
                       verse += verses.VerseText!!
               }}
bibleVerseList.title = bibleBook
    //Under further review for bugfixes
               //setContentView(R.layout.index_pager)
               val bibleAdapter = BibleReaderAdapter(
                   supportFragmentManager,
                   bibleVerseList,
                   bibleVerseList.title!!
               )

if(bibleVerseList.size>1)
{
    setContentView(R.layout.index_pager)
    val adapter = BibleReaderAdapter(supportFragmentManager,bibleVerseList,bibleVerseList.title!!)
    val vp = findViewById<ViewPager>(R.id.resultPager)
bibleReaderHandler!!.displayResults(bibleVerseList,vp,adapter,0)

}
else
{
    setContentView(R.layout.fragment_bible_view_results)
    val bibleContents = bibleVerseList[0]

    val chTextBox = findViewById<TextView>(R.id.chapterText)
    val chHeader = findViewById<TextView>(R.id.chapterHeader)

    chHeader.text = bibleContents.BookName +" " + bibleContents.ChapterNum +":"+ bibleContents .VerseNumber
    chTextBox.text = bibleContents.VerseText
}
           Log.i("VerseCatcher", "Results found " + bibleVerseList.count())
       } catch (ex: Exception) {
           ex.printStackTrace()
           MotionToast.createToast(
               this,
               "Error",
               ex.stackTraceToString(),
               MotionToast.TOAST_ERROR,
               MotionToast.GRAVITY_BOTTOM,
               MotionToast.LONG_DURATION,
               ResourcesCompat.getFont(applicationContext, R.font.helvetica_regular)
           )
       }
   }

    
}
class BibleReaderAdapter (fm: FragmentManager?, verseList: BibleContentsList, titleString:String ) : FragmentStatePagerAdapter(fm!!) {
    var dList1 = BibleContentsList()
    var bibleList = verseList
    private var bibleBookPosition = 0
    private var term = ""
    private val header = ""

    //public FragmentManager news;
    var news: FragmentManager? = null
    override fun getCount(): Int {
        return bibleList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (term === "") {
            term = bibleList.title!!//+" "+bibleList[position+1].ChapterNum
        }
        if(term=="")
        return String.format("Chapter %s of %s in %s", position + 1, bibleList.size, term)
        else
            return term
    }

    override fun getItem(position: Int): Fragment {
        var title = ""
        val frg: Fragment
        val bibleSection = bibleList[position]
        var bibleTopic: String? = ""
        bibleTopic = bibleSection.BookName!!
        bibleBookPosition++
        frg = BibleViewerFragment.NewVerse(bibleSection.ChapterNum!!,bibleSection.VerseText!!,bibleSection.VerseNumber!!,bibleSection.BookName!!)
        return frg
    }

    init {


//news = fm;
        term = titleString
    }
}
