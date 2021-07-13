package com.confessionsearchapptest.release1.ui.home

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.widget.ShareActionProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.confessionsearchapptest.release1.R
import com.confessionsearchapptest.release1.data.documents.DocumentList
import com.confessionsearchapptest.release1.data.documents.documentDBClassHelper
import com.confessionsearchapptest.release1.databinding.FragmentHomeBinding
import com.confessionsearchapptest.release1.searchhandlers.SearchHandler
import com.confessionsearchapptest.release1.searchresults.SearchFragmentActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class SearchFragment : Fragment() {
var docDBhelper : documentDBClassHelper? = null
    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentHomeBinding? = null
    var documentDB : SQLiteDatabase? = null
    var shareProvider: ShareActionProvider? = null

    private var documentTypeSpinner: Spinner? = null
    private var documentNameSpinner: Spinner? = null
    var helpButton: ExtendedFloatingActionButton? = null
    var searchButton: ExtendedFloatingActionButton? = null
    var notesButton: ExtendedFloatingActionButton? = null
    var header = ""
    protected var textSearch: Boolean? = null
    protected var questionSearch: Boolean? = null
    protected var readerSearch: Boolean? = null
    var query: String? = null
    var dbName = "confessionSearchDB.sqlite3"
    var documentDBHelper: documentDBClassHelper? = null
    var type = ""
    var shareList = ""

    var fileName: String? = null
    protected var allOpen: Boolean? = null
    protected var confessionOpen: Boolean? = null
    protected var catechismOpen: Boolean? = null
    protected var creedOpen: Boolean? = null
    protected var helpOpen: Boolean? = null
    protected var proofs = true
    protected var answers = true
    protected var searchAll = false



    //Testing
    var answerChip: Chip? = null
    var proofChip: Chip? = null
    var searchAllChip: Chip? = null
    var optionGroup: ChipGroup? = null

var searchFAB : ExtendedFloatingActionButton? = null
    var topicChip: Chip? = null
    var questionChip: Chip? = null
    var readDocsChip: Chip? = null
    var docTypeSpinnerAdapter: ArrayAdapter<String>? = null
    var docTitleSpinnerAdapter: ArrayAdapter<String>? = null
    var searchBox: SearchView? = null
    //var documentDB: SQLiteDatabase? = null
    var themeName: Boolean? = false
    var chipGroup: ChipGroup? = null
    var masterList = DocumentList()
    var shareNote: String? = null
    var searchFragment: SearchFragmentActivity? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
//Create Fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Load all objects related to Search Screen Here
searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
// Load database
//docDBhelper = documentDBClassHelper(super.getContext())
  //      documentDB = docDBhelper!!.readableDatabase
//Load Types and Load Spinners
        //searchViewModel.loadTypes(docDBhelper!!.getAllDocTypes(documentDB))
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val root: View = binding.root//View.inflate(context,R.layout.fragment_home,container)























        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private var checkBox = CompoundButton.OnCheckedChangeListener { compoundButton, _ ->
        when (compoundButton.id) {
            R.id.proofChip -> proofs = !proofChip!!.isChecked
            R.id.answerChip -> answers = !answerChip!!.isChecked
            R.id.searchAllChip -> searchAll = searchAllChip!!.isChecked
        }
    }
    var optionListener = ChipGroup.OnCheckedChangeListener { group, checkedId ->
        val enter = KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER)
        //val searchFab = root.findViewById<ExtendedFloatingActionButton>(R.id.searchFAB)
        if (checkedId == (R.id.topicChip)) {
            searchBox!!.isEnabled = true
            searchBox!!.imeOptions = EditorInfo.IME_ACTION_SEARCH
            searchBox!!.isSubmitButtonEnabled = true
            searchBox!!.setOnKeyListener(submissionKey)
            searchBox!!.setOnQueryTextListener(searchQueryListener)
            searchBox!!.inputType = InputType.TYPE_CLASS_TEXT
            textSearch = true
            questionSearch = false
            readerSearch = false
            searchFAB!!.text = resources.getString(R.string.Search)

        } else if (checkedId == R.id.questionChip) {
            searchBox!!.isEnabled = true
            searchBox!!.imeOptions = EditorInfo.IME_ACTION_SEARCH
            searchBox!!.inputType = InputType.TYPE_CLASS_NUMBER
            searchBox!!.setOnQueryTextListener(searchQueryListener)
            searchBox!!.setOnKeyListener(submissionKey)
            textSearch = false
            readerSearch = false
            questionSearch = true
            searchFAB.text = resources.getString(R.string.Search)

        } else if (checkedId == R.id.readDocsChip) {
            searchFAB!!.text = resources.getString(R.string.read_button_text)
            textSearch = false
            questionSearch = false
            readerSearch = true
        }

    }
    var searchButtonListener = View.OnClickListener {
        val query: String
        if (!readerSearch!!) {
            query = searchBox!!.query.toString()
            if (query.isEmpty()) /*ErrorMessage(resources.getString(R.string.query_error))*/
                Toast.makeText(super.getContext(),R.string.query_error,Toast.LENGTH_LONG).show()
                else Search(query)
        } else {
            query = ""
            Search(query)
        }
    }
    var submissionKey = View.OnKeyListener { v, keyCode, event ->
        val searchBox = v as SearchView
        if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
            query = searchBox.query.toString()
            Log.d("View", String.format("%s", event.displayLabel))
            if (!query!!.isEmpty() and !readerSearch!!) Search(query) else Toast.makeText(super.getContext(),R.string.query_error,Toast.LENGTH_LONG).show()
/*ErrorMessage(resources.getString(R.string.query_error))*/
            true
        } else {
            false
        }
    }

   /* fun ErrorMessage(message: String?) {
        //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        val errorBar = Snackbar.make(findViewById(R.id.layout_super), message!!, BaseTransientBottomBar.LENGTH_SHORT)
        errorBar.setAnchorView(R.id.relativeLayout)
        errorBar.show()
    }*/


    // 7-13-21 Take the data from the search form and package it in a format to put in the search handler
    fun Search(query: String?)
   {
        var searchIntent =Intent(super.getContext(), SearchHandler::class.java)
    val stringQuery = query
       var catechismOpenBool : Boolean
       var confessionOpenBool : Boolean
       var creedOpenBool : Boolean
    var searchAllBool : Boolean
    var allDocsBool: Boolean
       var readerSearchBool : Boolean
var questionSearchBool : Boolean
var proofsBool: Boolean
var answersBool : Boolean

//File Name stuff
/
    var documentListTransporter = masterList




    }

}