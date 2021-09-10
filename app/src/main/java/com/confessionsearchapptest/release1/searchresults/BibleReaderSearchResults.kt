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
import androidx.core.content.res.ResourcesCompat
import www.sanju.motiontoast.MotionToast
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

          /*DesignerToast.Success(
                    this,
                    String.format("Results found: "+bibleVerseList.count()),
                    Gravity.CENTER,
                    Toast.LENGTH_LONG

                )*/



           MotionToast.createToast(this,"Search Results","Results found " + bibleVerseList.count(),
               MotionToast.TOAST_SUCCESS,
               MotionToast.GRAVITY_BOTTOM
               ,
               MotionToast.LONG_DURATION,
               ResourcesCompat.getFont(applicationContext,R.font.helvetica_regular))
if(bibleCh!=0)
{
    var verse =""

if(bibleVerseNum!=0)
    verse=bibleVerseList[0].VerseText!!
    else
{for(verses in bibleVerseList)
            verse +=verses.VerseText!!}
        MotionToast.createToast(this,"Bible Chapter",verse!!,
            MotionToast.TOAST_SUCCESS,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(applicationContext,R.font.helvetica_regular))
    }

else
{
    MotionToast.createToast(this,"Search Results","Results found " + bibleVerseList.count(),
        MotionToast.TOAST_SUCCESS,
        MotionToast.GRAVITY_BOTTOM
        ,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(applicationContext,R.font.helvetica_regular))
}
           Log.i("VerseCatcher","Results found " + bibleVerseList.count())


       }
       catch(ex : Exception){
       ex.printStackTrace()
        MotionToast.createToast(this,"Error",ex.stackTraceToString(),MotionToast.TOAST_ERROR,MotionToast.GRAVITY_BOTTOM,MotionToast.LONG_DURATION,ResourcesCompat.getFont(applicationContext,R.font.helvetica_regular) )
       }
     
     
     
     }
    
}
