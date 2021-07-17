package com.confessionsearchapptest.release1.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.confessionsearchapptest.release1.MainActivity
import com.confessionsearchapptest.release1.data.documents.DocumentTitle
import com.confessionsearchapptest.release1.data.documents.DocumentType
import com.confessionsearchapptest.release1.data.documents.documentDBClassHelper
//Handles importing the Database for the search engine
class SearchViewModel : ViewModel() {

    //Document List variables for loading on main search form
    private var documentTypes: ArrayList<String?> = ArrayList()
    private var documentTitleList: ArrayList<String?> = ArrayList()
    // Unnecessary code
   /* fun setTypes(list: ArrayList<DocumentType?>) {
        documentTypes.add("All")
        for (type in list)
            documentTypes.add(type!!.documentTypeName)

    }*/

    fun getTypes(): ArrayList<String?> {
        return documentTypes
    }

    fun loadTypes(list: ArrayList<DocumentType?>) {
        documentTypes.add("All")
        for (type in list)
            documentTypes.add(type!!.documentTypeName)

    }

    fun getTitles(): ArrayList<String?> {
        return documentTitleList
    }

    fun loadTitles(list: ArrayList<DocumentTitle?>) {
        for (docTitle in list)
            documentTitleList.add(docTitle!!.documentName)

    }


}