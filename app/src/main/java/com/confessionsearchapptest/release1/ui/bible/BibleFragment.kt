package com.confessionsearchapptest.release1.ui.bible

import android.content.Intent
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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.confessionsearchapptest.release1.R
import com.confessionsearchapptest.release1.data.documents.documentDBClassHelper
import com.confessionsearchapptest.release1.databinding.FragmentBibleFormBinding
import com.confessionsearchapptest.release1.searchresults.BibleReaderSearchResults

@Suppress("MemberVisibilityCanBePrivate")
class BibleFragment : Fragment() {

    private var _binding: FragmentBibleFormBinding? = null
    private lateinit var bibleViewModel: BibleViewModel
var documentDB : SQLiteDatabase? = null
    var docDBhelper : documentDBClassHelper?= null
    var bibleTransList : ArrayList<String?> = ArrayList()
    var bibleTranslation =""
    var submitButton :  ExtendedFloatingActionButton? = null

      var bibleCh =0
     var bibleVerseNum=0
    var bibleBooksList : ArrayList<String?> = ArrayList()
    var bibleChapterList : ArrayList<Int?> = ArrayList()
    var bibleVerseNumList : ArrayList<Int?> = ArrayList()
    var bibleSelectorSpinner : Spinner? = null
    var bibleChapterSpinner : Spinner? = null
var bibleVerseSelector : Spinner?= null
    var bibleBook =""
    var bibleBookSelectorComboBox:Spinner?=null
    var bibleBookAdapter : ArrayAdapter<String>? = null
var bibleChNumAdapter : ArrayAdapter<Int>?=null
var bibleVerseNumAdapter : ArrayAdapter<Int>?=null

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
//Translations
      bibleViewModel.loadTranslations(docDBhelper!!.getAllBibleTranslations(documentDB!!))
bibleTransList = bibleViewModel.getTranslations()
       val root =binding.root
        bibleSelectorSpinner = root.findViewById(R.id.bibleTranslationSelector)
        bibleSelectorAdapter = ArrayAdapter(requireContext(),
            R.layout.support_simple_spinner_dropdown_item,bibleTransList)
        bibleSelectorSpinner!!.adapter = bibleSelectorAdapter
        bibleSelectorSpinner!!.onItemSelectedListener=bibleSelectorSpinnerListener
bibleBookSelectorComboBox = root.findViewById(R.id.bibleBookSelector)
bibleChapterSpinner = root.findViewById(R.id.bibleChapterSpinner)
        bibleVerseSelector = root.findViewById(R.id.verseSpinner)
       bibleTranslation = bibleSelectorSpinner!!.selectedItem.toString()
submitButton = root.findViewById(R.id.submitFAB)
submitButton!!.setOnClickListener(submitFabClicker)

        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    var bibleSelectorSpinnerListener: AdapterView.OnItemSelectedListener = object:
    AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            bibleTranslation = String.format("%s", parent!!.selectedItem.toString())
            bibleViewModel.loadBooks(docDBhelper!!.getAllBooks(documentDB!!))
            bibleBooksList = bibleViewModel.getBooks()
            bibleBookAdapter = ArrayAdapter(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                bibleBooksList
            )
            bibleBookSelectorComboBox!!.adapter = bibleBookAdapter
            bibleBookSelectorComboBox!!.onItemSelectedListener = bibleBookSelectorListener
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            bibleTranslation = String.format("%s", parent!!.selectedItem.toString())
        }
    }

    var bibleBookSelectorListener :AdapterView.OnItemSelectedListener = object :
    AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            bibleBook = String.format("%s", parent!!.selectedItem.toString())
            bibleViewModel.loadChapters(docDBhelper!!.getAllChapters(documentDB,bibleTranslation,bibleBook))
            bibleChapterList = bibleViewModel.getChapters()
            bibleChNumAdapter= ArrayAdapter(requireContext(),R.layout.support_simple_spinner_dropdown_item,bibleChapterList)

bibleChapterSpinner!!.adapter=bibleChNumAdapter
bibleChapterSpinner!!.onItemSelectedListener=bibleChSelectorListener



        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            bibleBook= String.format("%s",parent!!.selectedItem.toString())
            bibleViewModel.loadChapters(docDBhelper!!.getAllChapters(documentDB,bibleTranslation,bibleBook))
            bibleChapterList = bibleViewModel.getChapters()
            bibleChNumAdapter= ArrayAdapter(requireContext(),R.layout.support_simple_spinner_dropdown_item,bibleChapterList)
            bibleChapterSpinner!!.adapter=bibleChNumAdapter
            bibleChapterSpinner!!.onItemSelectedListener=bibleChSelectorListener
        }
    }


    var bibleChSelectorListener : AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            bibleCh= parent!!.selectedItem.toString().toInt()
            bibleViewModel.loadVerseNumbers(docDBhelper!!.getAllVerseNumbers(documentDB!!,bibleTranslation,bibleBook,bibleCh))
            bibleVerseNumList.add(0)
            bibleVerseNumList.addAll(bibleViewModel.getVerseNumbers())
            bibleVerseNumAdapter= ArrayAdapter(requireContext(),R.layout.support_simple_spinner_dropdown_item,bibleVerseNumList)
            bibleVerseSelector!!.adapter=bibleVerseNumAdapter
            bibleVerseSelector!!.onItemSelectedListener=bibleVerseSelectorListener
        }
        override fun onNothingSelected(parent: AdapterView<*>?) {
            bibleCh= parent!!.selectedItem.toString().toInt()
        }
    }
    var bibleVerseSelectorListener : AdapterView.OnItemSelectedListener = object :AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            bibleVerseNum = parent!!.selectedItem.toString().toInt()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            bibleVerseNum = parent!!.selectedItem.toString().toInt()
        }
    }
    var submitFabClicker=View.OnClickListener {
        val bibleIntent = Intent(context, BibleReaderSearchResults::class.java)
//bibleViewModel.loadBibleList(docDBhelper!!.getChaptersandVerses(documentDB!!,bibleTranslation,bibleBook,bibleCh,bibleVerseNum)
bibleIntent.putExtra("Translation",bibleTranslation)
bibleIntent.putExtra("BookName",bibleBook)
bibleIntent.putExtra("Chapter",bibleCh)
bibleIntent.putExtra("VerseNum",bibleVerseNum)
requireContext().startActivity(bibleIntent)


    }
    companion object{
    const val ACTIVITY_ID = 37
    }
}
