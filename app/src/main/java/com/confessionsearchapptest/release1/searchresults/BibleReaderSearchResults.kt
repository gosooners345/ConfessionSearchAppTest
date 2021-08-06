package com.confessionsearchapptest.release1.searchresults

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.confessionsearchapptest.release1.data.documents.DocumentList


class BibleReaderSearchResults : AppCompatActivity() {
var bibleVP : ViewPager?=null
    var adapter: SearchAdapter? = null
    var shareList: String? = null
    var header = ""
    var index: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}