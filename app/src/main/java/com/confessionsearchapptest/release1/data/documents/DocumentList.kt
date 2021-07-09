package com.confessionsearchapptest.release1.data.documents

import java.util.*

class DocumentList : ArrayList<Document>() {
    var title: String? = null

    init {
        this.title = ""
    }
}