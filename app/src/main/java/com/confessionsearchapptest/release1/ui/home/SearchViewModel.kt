package com.confessionsearchapptest.release1.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.confessionsearchapptest.release1.MainActivity
import com.confessionsearchapptest.release1.data.documents.DocumentTitle
import com.confessionsearchapptest.release1.data.documents.DocumentType
import com.confessionsearchapptest.release1.data.documents.documentDBClassHelper

class SearchViewModel : ViewModel() {

    private var documentTypes: ArrayList<String?> = ArrayList()
    private var documentTitleList: ArrayList<String?> = ArrayList()

    /*MutableLiveData<ArrayList<DocumentType>>by lazy {
        MutableLiveData<ArrayList<DocumentType>>().also{ loadTypes()}
    }*/
    fun setTypes(list: ArrayList<DocumentType?>) {
        documentTypes.add("All")
        for (type in list)
            documentTypes.add(type!!.documentTypeName)

    }

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