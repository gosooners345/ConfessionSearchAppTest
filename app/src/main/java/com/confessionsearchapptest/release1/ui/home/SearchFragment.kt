package com.confessionsearchapptest.release1.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.widget.ShareActionProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.confessionsearchapptest.release1.R

import com.confessionsearchapptest.release1.data.documents.DocumentList
import com.confessionsearchapptest.release1.data.documents.documentDBClassHelper
import com.confessionsearchapptest.release1.databinding.FragmentHomeBinding
import com.confessionsearchapptest.release1.searchhandlers.SearchHandler
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.vdx.designertoast.DesignerToast
import java.util.*
import kotlin.collections.ArrayList
class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentHomeBinding? = null
    var documentDB: SQLiteDatabase? = null
    var docDBhelper: documentDBClassHelper? = null
    var shareProvider: ShareActionProvider? = null
private var translationSpinner : Spinner? = null
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
   // var documentDBHelper: documentDBClassHelper? = null
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

    var searchFAB: ExtendedFloatingActionButton? = null
    var topicChip: Chip? = null
    var questionChip: Chip? = null
    var readDocsChip: Chip? = null
    var docTypeSpinnerAdapter: ArrayAdapter<String>? = null
    var bibleTranslationAdapter: ArrayAdapter<String>? = null
    var docTitleSpinnerAdapter: ArrayAdapter<String>? = null
    var bibleTransList : ArrayList<String?> = ArrayList()
    var docTitleList: ArrayList<String?> = ArrayList()
    var docTypes: ArrayList<String?> = ArrayList()
    var searchBox: SearchView? = null
var translationAbbrevTitle =""
    //var documentDB: SQLiteDatabase? = null

    var chipGroup: ChipGroup? = null
    var masterList = DocumentList()
    var shareNote: String? = null
    //var searchFragment: SearchFragmentActivity? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //Create Fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Load all objects related to Search Screen Here
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        // Load database
        docDBhelper = documentDBClassHelper(super.getContext())
        documentDB=docDBhelper!!.readableDatabase
        //Load Types and Load Spinners
        searchViewModel.loadTypes(docDBhelper!!.getAllDocTypes(documentDB))

        val root: View = binding.root//View.inflate(context,R.layout.fragment_home,container)
        //Chip Group Initialization
        chipGroup = root.findViewById(R.id.chip_group)
        optionGroup = root.findViewById(R.id.option_group)
        //Search Button Initialization
        searchButton = root.findViewById(R.id.searchFAB)

        searchButton!!.setOnClickListener(searchButtonListener)
        //Search Box Initialization
        searchBox = root.findViewById(R.id.searchView1)
        searchBox!!.setOnQueryTextListener(searchQueryListener)
        searchBox!!.setOnKeyListener(submissionKey)
        //More stuff
        optionGroup!!.setOnCheckedChangeListener(optionListener)

        // Chip Initialization 06/01/2021 - Testing look and execution
        answerChip = root.findViewById(R.id.answerChip)
        proofChip = root.findViewById(R.id.proofChip)
        searchAllChip = root.findViewById(R.id.searchAllChip)

        //Implement check changed listeners
        answerChip!!.setOnCheckedChangeListener(checkBox)
        proofChip!!.setOnCheckedChangeListener(checkBox)
        searchAllChip!!.setOnCheckedChangeListener(checkBox)
        topicChip = root.findViewById(R.id.topicChip)
        questionChip = root.findViewById(R.id.questionChip)
        readDocsChip = root.findViewById(R.id.readDocsChip)
        //Spinner Initialization
        documentTypeSpinner = root.findViewById(R.id.documentTypeSpinner)
        documentNameSpinner = root.findViewById(R.id.documentNameSpinner)
        translationSpinner = root.findViewById(R.id.bibleTranslationSpinner)
        //Adapter and Spinner Assignments
        docTypes = searchViewModel.getTypes()
        docTypeSpinnerAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            docTypes!!
        )
        documentTypeSpinner!!.adapter = docTypeSpinnerAdapter
        documentTypeSpinner!!.onItemSelectedListener = spinnerItemSelectedListener
        type = ""
        //Load Document Titles into Doc Title list for preparation
        searchViewModel.loadTitles(docDBhelper!!.getAllDocTitles(type, documentDB!!))
        docTitleList = searchViewModel.getTitles()
        docTitleSpinnerAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            docTitleList
        )
        documentNameSpinner!!.onItemSelectedListener = docTitleSpinner

        //Bible Translation Spinner Initialization
        searchViewModel.loadTranslations(docDBhelper!!.getAllBibleTranslations(documentDB!!))
        bibleTransList = searchViewModel.getTranslations()
        bibleTranslationAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            bibleTransList
        )
        translationSpinner!!.adapter=bibleTranslationAdapter
        translationSpinner!!.onItemSelectedListener = translationSpinnerItemSelectedListener

        searchBox!!.setOnKeyListener(submissionKey)
        topicChip!!.performClick()

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
           // searchFAB!!.text = resources.getString(R.string.Search)

        } else if (checkedId == R.id.questionChip) {
            searchBox!!.isEnabled = true
            searchBox!!.imeOptions = EditorInfo.IME_ACTION_SEARCH
            searchBox!!.inputType = InputType.TYPE_CLASS_NUMBER
            searchBox!!.setOnQueryTextListener(searchQueryListener)
            searchBox!!.setOnKeyListener(submissionKey)
            textSearch = false
            readerSearch = false
            questionSearch = true
            //searchFAB!!.text = resources.getString(R.string.Search)

        } else if (checkedId == R.id.readDocsChip) {
         //   searchFAB!!.text = resources.getString(R.string.read_button_text)
            textSearch = false
            questionSearch = false
            readerSearch = true
        }

    }
    //Submission key
    var searchButtonListener = View.OnClickListener {
        val query: String
        if (!readerSearch!!) {
            query = searchBox!!.query.toString()
            if (query.isEmpty()) /*ErrorMessage(resources.getString(R.string.query_error))*/
            {  DesignerToast.Error(
                    super.getContext(),
                    "Enter A topic in the search field!",
                    Gravity.BOTTOM,
                    Toast.LENGTH_LONG
                    
                )
             MotionToast.makeText(
                        context,
                        "Enter a value in the Search Field",
MotionToast.TOAST_ERROR,MotionToast.GRAVITY_BOTTOM,MotionToast.LONG_DURATION)
            }
                 MotionToast.makeText(
                        context,
                        "Enter a value in the Search Field",
MotionToast.TOAST_ERROR,MotionToast.GRAVITY_BOTTOM,MotionToast.LONG_DURATION)
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
            if (!query!!.isEmpty() and !readerSearch!!) Search(query) else  MotionToast.makeText(
                        context,
                        "Enter a value in the Search Field",
MotionToast.TOAST_ERROR,MotionToast.GRAVITY_BOTTOM,MotionToast.LONG_DURATION)
/*ErrorMessage(resources.getString(R.string.query_error))*/
            true
        } else {
            false
        }
    }
    var searchQueryListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(entry: String): Boolean {
                query = entry
                if (!readerSearch!!) {
                    if (query!!.isEmpty()) MotionToast.makeText(
                        context,
                        "Enter a value in the Search Field",
MotionToast.TOAST_ERROR,MotionToast.GRAVITY_BOTTOM,MotionToast.LONG_DURATION)
                    else Search(query)
                } else Search(query)
                return false
            }

            //nothing happens here
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        }

    //Spinner Listeners
            // Bible Translation Spinner Listener
    var translationSpinnerItemSelectedListener : AdapterView.OnItemSelectedListener = object :
    AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            run{
            translationAbbrevTitle = String.format("%s",parent!!.selectedItem.toString())
            }
                    }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            translationAbbrevTitle = parent!!.selectedItem.toString()
        }
    }
    //Document type spinner listener
    var spinnerItemSelectedListener: AdapterView.OnItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
        @SuppressLint("ResourceAsColor")
        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            run {
                val docTitles: ArrayList<String?> = ArrayList()
                type = parent.selectedItem.toString()
                //Gets all document titles and places them in a list
                for (docTitle in docDBhelper!!.getAllDocTitles(type, documentDB!!)) {
                    docTitles!!.add(docTitle.documentName!!)
                }
                docTitleSpinnerAdapter = ArrayAdapter(
                    requireContext(),
                    R.layout.support_simple_spinner_dropdown_item,
                    docTitles
                )
                docTitleSpinnerAdapter!!.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                documentNameSpinner!!.adapter = docTitleSpinnerAdapter
                documentNameSpinner!!.onItemSelectedListener = docTitleSpinner
                when (type.uppercase(Locale.getDefault())) {
                    "ALL" -> {
                        allOpen = true
                        confessionOpen = false
                        catechismOpen = false
                        creedOpen = false
                        helpOpen = false
                    }
                    "CONFESSION" -> {
                        allOpen = false
                        confessionOpen = true
                        catechismOpen = false
                        header = "Chapter "
                        creedOpen = false
                        helpOpen = false
                    }
                    "CATECHISM" -> {
                        allOpen = false
                        header = "Question "
                        confessionOpen = false
                        catechismOpen = true
                        creedOpen = false
                        helpOpen = false
                    }
                    "CREED" -> {
                        allOpen = false
                        creedOpen = true
                        catechismOpen = false
                        confessionOpen = false
                        helpOpen = false
                    }
                }
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            type = parent.selectedItem.toString()
        }
    }
    var docTitleSpinner: AdapterView.OnItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {
        override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
            fileName = String.format("%s", adapterView.selectedItem.toString())
        }

        override fun onNothingSelected(adapterView: AdapterView<*>?) {
            fileName = adapterView!!.selectedItem.toString()
        }
    }


    // 7-13-21 Take the data from the search form and package it in a format to put in the search handler
    @SuppressLint("NewApi")
    fun Search(query: String?) {

        var searchIntent = Intent(context, SearchHandler::class.java)//MainActivity::class.java)
        //val parentActivity = super.getActivity()
        val stringQuery = query
        Log.d("Test", context.toString())
        //Document Type Filtering
        searchIntent.putExtra("AllDocs", allOpen)
        searchIntent.putExtra("Confession", confessionOpen)
        searchIntent.putExtra("Catechism", catechismOpen)
        searchIntent.putExtra("Creed", creedOpen)
        //All document search within type or all
        searchIntent.putExtra("SearchAll", searchAll)
        //Search Type
        searchIntent.putExtra("Question", questionSearch)
        searchIntent.putExtra("Text", textSearch)
        searchIntent.putExtra("Reader", readerSearch)
        //Advanced Options
        searchIntent.putExtra("Answers", answers)
        searchIntent.putExtra("Proofs", proofs)
        //Query Holder
        searchIntent.putExtra("Query", stringQuery)
        //FileName
        searchIntent.putExtra("FileName", fileName)
        searchIntent.putExtra("ACTIVITY_ID", ACTIVITY_ID)
        requireContext().startActivity(searchIntent)
    }
companion object{
    const val ACTIVITY_ID = 32
}

}

