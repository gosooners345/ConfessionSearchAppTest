package com.confessionsearchapptest.release1.ui.bible

import androidx.lifecycle.ViewModel
import com.confessionsearchapptest.release1.data.bible.BibleBooks
import com.confessionsearchapptest.release1.data.bible.BibleContentsList
import com.confessionsearchapptest.release1.data.bible.BibleTranslation

class BibleViewModel : ViewModel() {
    private var bibleBookList: ArrayList<String?> = ArrayList()
    private var bibleTranslationList: ArrayList<String?> = ArrayList()

    private var bibleChapterList: ArrayList<Int?> = ArrayList()
    private var bibleVerseNumList: ArrayList<Int?> = ArrayList()
    private var bibleVerseList: ArrayList<String?> = ArrayList()

    fun getBooks(): ArrayList<String?> {

        return bibleBookList
    }

    fun loadBooks(list: ArrayList<BibleBooks>) {
      // var bookName = list[0].BookName!!
        //bibleBookList.add(bookName)
        for (book in list)
        {
            bibleBookList.add(book.BookName)
            /*if(book.BookName!=bookName) {
             bookName=book.BookName!!
                bibleBookList.add(book.BookName!!)
            }*/
        }
    }

    fun getChapters(): ArrayList<Int?> {
        return bibleChapterList
    }

    fun loadChapters(list: ArrayList<Int?>) {


            //var num = chapter.ChapterNum
            bibleChapterList=list

    }
    fun getVerseNumbers(): ArrayList<Int?> {
        return bibleVerseNumList
    }

    fun loadVerseNumbers(list: BibleContentsList) {
        for (verseNum in list)
            bibleVerseNumList.add(verseNum.VerseNumber!!)
    }

    fun getVerses(): ArrayList<String?> {
        return bibleVerseList
    }

    fun loadVerses(list: BibleContentsList) {
        for (verse in list)
            bibleVerseList.add(verse.VerseText)
    }

    fun getTranslations(): ArrayList<String?> {
        return bibleTranslationList
    }

    fun loadTranslations(list: ArrayList<BibleTranslation>)
    {
        for(translation in list)
            bibleTranslationList.add(translation.bibleTranslationName)
    }


}