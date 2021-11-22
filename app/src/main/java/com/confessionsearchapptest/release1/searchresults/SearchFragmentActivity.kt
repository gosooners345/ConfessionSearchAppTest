package com.confessionsearchapptest.release1.searchresults

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.confessionsearchapptest.release1.R
import com.confessionsearchapptest.release1.data.documents.DocumentList
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragmentActivity  //public ViewPager2 vp2;
    : AppCompatActivity() {
    var adapter: SearchAdapter? = null
    var shareList: String? = null
    var documentList = DocumentList()
    var files: String? = null
    var header = ""
    var index: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun DisplayResults(sourceList: DocumentList, vp: ViewPager2, search: SearchAdapter, searchTerm: String?, count: Int) {

        documentList = sourceList
        var title =""
        search.createFragment(count)
        search.saveState()
        vp.adapter = search


    }



}

//This is where the biggest changes appear to occur
class SearchAdapter(fm:FragmentManager, documents: DocumentList, searchTerm: String,lifeCycle: Lifecycle) : FragmentStateAdapter(fm!!,lifeCycle) {
    var dList1 = DocumentList()
    var documentList1 = DocumentList()
    private var docPosition = 0
    private var term = ""
    private val header = ""

    //public FragmentManager news;
   // var news: FragmentManager? = null
    override fun getItemCount(): Int {
        return documentList1.size
    }

    init {
        documentList1 = documents
        term = searchTerm
    }

    override fun createFragment(position: Int): Fragment {
        var title = ""
        val frg: Fragment
        val document = documentList1[position]
        var docTitle: String? = ""
        title = document.documentName!!
        docTitle = if (title === "Results" || title === "") document.documentName else document.documentName
        docPosition++

        frg = SearchResultFragment.NewResult(document.documentText, document.proofs, document.documentName,
            document.chNumber, documentList1.title, document.matches, document.chName, document.tags)
        return frg
    }
}