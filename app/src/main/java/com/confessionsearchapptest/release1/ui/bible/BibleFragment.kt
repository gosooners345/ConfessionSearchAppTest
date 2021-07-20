package com.confessionsearchapptest.release1.ui.bible

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.confessionsearchapptest.release1.R
import com.confessionsearchapptest.release1.data.documents.documentDBClassHelper
import com.confessionsearchapptest.release1.databinding.FragmentBibleFormBinding

class BibleFragment : Fragment() {

    private var _binding: FragmentBibleFormBinding? = null
    private lateinit var bibleViewModel: BibleViewModel
var documentDB : SQLiteDatabase? = null
    var docDBhelper : documentDBClassHelper?= null
    var bibleTransList : ArrayList<String?> = ArrayList()
    var bibleTranslation =""

    lateinit  var bibleCh : Integer
    var bibleBooksList : ArrayList<String?> = ArrayList()
    var bibleChapterList : ArrayList<Int?> = ArrayList()
    var bibleVerseNumList : ArrayList<Int?> = ArrayList()
    var bibleSelectorSpinner : Spinner? = null

    var bibleBook =""
    var bibleBookSelectorComboBox:Spinner?=null
    var bibleBookAdapter : ArrayAdapter<String>? = null

var bibleSelectorAdapter : ArrayAdapter<String>? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBibleFormBinding.inflate(inflater, container, false)
        bibleViewModel = ViewModelProvider(this).get(BibleViewModel::class.java)
        docDBhelper = documentDBClassHelper(super.getContext())
        documentDB=docDBhelper!!.readableDatabase

      bibleViewModel.loadTranslations(docDBhelper!!.getAllBibleTranslations(documentDB!!))
bibleTransList = bibleViewModel.getTranslations()
       val root =binding.root
        bibleSelectorSpinner = root.findViewById(R.id.bibleTranslationSelector)
        bibleSelectorAdapter = ArrayAdapter(requireContext(),
            R.layout.support_simple_spinner_dropdown_item,bibleTransList)
        bibleSelectorSpinner!!.adapter = bibleSelectorAdapter
        bibleSelectorSpinner!!.onItemSelectedListener=bibleSelectorSpinnerListener
bibleBookSelectorComboBox = root.findViewById(R.id.bibleBookSelector)


        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    var bibleSelectorSpinnerListener: AdapterView.OnItemSelectedListener = object:
    AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            bibleTranslation = String.format("%s",parent!!.selectedItem.toString())
bibleViewModel.loadBooks(docDBhelper!!.getAllBooks(documentDB!!,bibleTranslation!!))
            bibleBooksList = bibleViewModel.getBooks()
            bibleBookAdapter = ArrayAdapter(requireContext(),R.layout.support_simple_spinner_dropdown_item,bibleBooksList)
            bibleBookSelectorComboBox!!.adapter=bibleBookAdapter
            bibleBookSelectorComboBox!!.onItemSelectedListener=bibleBookSelectorListener
                    }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            bibleTranslation= String.format("%s",parent!!.selectedItem.toString())
        }
    }

    var bibleBookSelectorListener :AdapterView.OnItemSelectedListener = object :
    AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
bibleBook= String.format("%s",parent!!.selectedItem.toString())
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            bibleBook= String.format("%s",parent!!.selectedItem.toString())
        }
    }
}