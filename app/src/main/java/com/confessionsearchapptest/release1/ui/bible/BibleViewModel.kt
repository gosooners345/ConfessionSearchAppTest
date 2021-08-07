package com.confessionsearchapptest.release1.ui.bible

import androidx.lifecycle.ViewModel
import com.confessionsearchapptest.release1.data.bible.BibleBooks
import com.confessionsearchapptest.release1.data.bible.BibleContentsList
import com.confessionsearchapptest.release1.data.bible.BibleTranslation

class BibleViewModel : ViewModel() {
    private var bibleBookList: ArrayList<String?> = ArrayList()
    private var bibleTranslationList: ArrayList<String?> = ArrayList()
    //private var bibleContentList = ArrayList<BibleContents> = ArrayList()
    private var bibleContentList=BibleContentsList()
    private var bibleChapterList: ArrayList<Int?> = ArrayList()
    private var bibleVerseNumList: ArrayList<Int?> = ArrayList()
    private var bibleVerseList: ArrayList<String?> = ArrayList()

    fun getBooks(): ArrayList<String?>
    {
        return bibleBookList
    }

    fun loadBooks(list: ArrayList<BibleBooks>)
    {
        for (book in list) {
            bibleBookList.add(book.BookName)
        }
    }

    fun getChapters(): ArrayList<Int?> {
        return bibleChapterList
    }

    fun loadChapters(list: ArrayList<Int?>) {
            bibleChapterList=list
    }
    fun getVerseNumbers(): ArrayList<Int?> {
        return bibleVerseNumList
    }

    fun loadVerseNumbers(list: ArrayList<Int?>) {
       bibleVerseNumList = list
    }

    fun getVerses(): ArrayList<String?> {
        return bibleVerseList
    }

    fun loadVerses(list: ArrayList<String?>) {
        for (verse in list)
            bibleVerseList.add(verse)
    }

    fun getTranslations(): ArrayList<String?> {
        return bibleTranslationList
    }

    fun loadTranslations(list: ArrayList<BibleTranslation>)
    {
        for(translation in list)
            bibleTranslationList.add(translation.bibleTranslationName)
    }
fun loadBibleList(list : BibleContentsList())
    {
        bibleContentList = list
    }
    fun getBibleList():BibleContentsList()
    {
    return bibleContentList
    }

}
