package com.confessionsearchapptest.release1.ui.home

import androidx.lifecycle.ViewModel
import com.confessionsearchapptest.release1.data.bible.BibleTranslation
import com.confessionsearchapptest.release1.data.documents.DocumentTitle
import com.confessionsearchapptest.release1.data.documents.DocumentType

//Handles importing the Database for the search engine
class SearchViewModel : ViewModel() {

    //Document List variables for loading on main search form
    private var documentTypes: ArrayList<String?> = ArrayList()
    private var documentTitleList: ArrayList<String?> = ArrayList()
    private var bibleTranslationList: ArrayList<String?> = ArrayList()
    //Document types
    fun getTypes(): ArrayList<String?> {
        return documentTypes
    }

    fun loadTypes(list: java.util.ArrayList<DocumentType>) {
        documentTypes.add("All")
        for (type in list)
            documentTypes.add(type!!.documentTypeName)

    }

    fun getTitles(): ArrayList<String?> {
        return documentTitleList
    }

    fun loadTitles(list: java.util.ArrayList<DocumentTitle>) {
        for (docTitle in list)
            documentTitleList.add(docTitle!!.documentName)

    }

    fun loadTranslations(list: java.util.ArrayList<BibleTranslation>) {
        for (translation in list)
            bibleTranslationList.add(translation!!.bibleTranslationName)
    }

    fun getTranslations(): ArrayList<String?> {
        return bibleTranslationList
    }
}