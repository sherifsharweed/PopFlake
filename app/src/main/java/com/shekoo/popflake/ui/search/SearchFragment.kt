package com.shekoo.popflake.ui.search

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shekoo.popflake.MainActivity
import com.shekoo.popflake.databinding.FragmentSearchBinding
import com.shekoo.popflake.utilities.Network
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SearchAdapter
    private val searchViewModel: SearchViewModel by viewModels()
    private var dialog: AlertDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        if(!Network.hasInternet(requireContext())){
            Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createAdapter()
    }

    override fun onResume() {
        super.onResume()
        binding.deleteSearchImageView.setOnClickListener {
            binding.searchTextView.text.clear()
        }

        binding.searchClickImageView.setOnClickListener {
            searchViewModel.getSearch(binding.searchTextView.text.toString())
        }

        lifecycleScope.launch {
            searchViewModel.searchData.collectLatest {
                searchAdapter.addList(it.results ?: emptyList())
            }
        }

        lifecycleScope.launch {
            searchViewModel.loadingData.collectLatest {
                if (it) showLoading() else hideLoading()
            }
        }

        /*lifecycleScope.launchWhenStarted {
            searchViewModel.searchError.collect {
                if (it != "") {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }*/
    }

    private fun createAdapter() {
        searchAdapter = SearchAdapter()
        binding.apply {
            searchRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            searchRecyclerView.adapter = searchAdapter
        }
    }


    private fun showLoading() {
        dialog = ProgressDialog.show(requireContext(), "", "Loading. Please wait...", true);
    }

    private fun hideLoading() {
        dialog?.dismiss()
    }

}