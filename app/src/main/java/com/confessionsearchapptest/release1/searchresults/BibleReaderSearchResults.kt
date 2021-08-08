package com.confessionsearchapptest.release1.searchresults

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.confessionsearchapptest.release1.data.documents.DocumentList
import com.confessionsearchapptest.release1.data.bible.BibleContentsList
import com.confessionsearchapptest.release1.data.documents.documentDBClassHelper
import com.confessionsearchapptest.release1.MainActivity
import com.confessionsearchapptest.release1.ui.bible.BibleFragment
import com.confessionsearchapptest.release1.R
import android.database.sqlite.SQLiteDatabase
import com.vdx.designertoast.DesignerToast
import android.view.Gravity
import android.view.View
import android.widget.Toast
import android.os.Build
import com.confessionsearchapptest.release1.ui.NotesActivity.NotesComposeActivity
import android.widget.TextView
import android.text.Html
import android.util.Log
import android.content.Intent
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.example.awesomedialog.*
import androidx.core.content.ContextCompat
import java.util.*
import java.util.regex.Pattern





class BibleReaderSearchResults : AppCompatActivity() {
var bibleVP : ViewPager?=null
    var adapter: SearchAdapter? = null
    var shareList: String? = null
    var bibleVerseList = BibleContentsList()
    var docDBhelper: documentDBClassHelper? = null
    var documentDB : SQLiteDatabase? = null
    var header = ""

  
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
val bibleBookName = intent.getStringExtra("BookName")
val bibleChapterNum = intent.getIntExtra("Chapter",0)
val bibleVerseNum = intent.getIntExtra("VerseNum",0)
val translationName = intent.getStringExtra("Translation")
 bibleReader(bibleBookName,bibleChapterNum,bibleVerseNum,translationName)

        
    }
    
    
   
    fun bibleReader( bibleBook : String?, bibleCh : Int?, bibleVerseNum : Int? , bibleTranslation : String?)
     {
         Log.d("BibleReader","Debut")
         docDBhelper= documentDBClassHelper(this)
         documentDB = docDBhelper!!.readableDatabase
       try{
         bibleVerseList = docDBhelper!!.getChaptersandVerses(documentDB!!,bibleTranslation,bibleBook,bibleCh,bibleVerseNum)
          DesignerToast.Success(
                    this,
                    String.format("Results found: "+bibleVerseList.count()),
                    Gravity.CENTER,
                    Toast.LENGTH_LONG
                )
       }
       catch(ex : Exception){
       ex.printStackTrace()
        DesignerToast.Error(
                    this,
                    String.format(ex.message!!.toString()),
                    Gravity.CENTER,
                    Toast.LENGTH_LONG
                )
       }
     
     
     
     }
    
}
