package com.confessionsearchapptest.release1.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.confessionsearchapptest.release1.R
import com.confessionsearchapptest.release1.databinding.FragmentHomeBinding

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = View.inflate(context,R.layout.fragment_home,container)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}