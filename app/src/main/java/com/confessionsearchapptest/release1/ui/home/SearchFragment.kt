package com.confessionsearchapptest.release1.ui.home

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.confessionsearchapptest.release1.R
import com.confessionsearchapptest.release1.data.documents.documentDBClassHelper
import com.confessionsearchapptest.release1.databinding.FragmentHomeBinding

class SearchFragment : Fragment() {
var docDBhelper : documentDBClassHelper? = null
    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentHomeBinding? = null
var documentDB : SQLiteDatabase? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

docDBhelper = documentDBClassHelper(super.getContext())
        documentDB = docDBhelper!!.readableDatabase



        searchViewModel.loadTypes(docDBhelper!!.getAllDocTypes(documentDB))
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root//View.inflate(context,R.layout.fragment_home,container)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}