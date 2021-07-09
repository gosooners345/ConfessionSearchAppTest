package com.confessionsearchapptest.release1.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.confessionsearchapptest.release1.MainActivity
import com.confessionsearchapptest.release1.data.documents.DocumentType
import com.confessionsearchapptest.release1.data.documents.documentDBClassHelper

class SearchViewModel : ViewModel() {

private val documentTypes : MutableLiveData<List<DocumentType>>by lazy {
    MutableLiveData<List<DocumentType>>().also
    {
        loadTypes()
    }}

    fun getTypes(): LiveData<List<DocumentType>>
    {
        return documentTypes
    }

    private fun loadTypes() {
        com.confessionsearchapptest.release1.MainActivity
        TODO("Not yet implemented")
    }

}