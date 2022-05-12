package com.shekoo.popflake.ui.home

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shekoo.popflake.MyApplication
import com.shekoo.popflake.databinding.FragmentHomeBinding
import com.shekoo.popflake.model.entities.TopMovies
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var topMoviesAdapter: TopMoviesAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createAdapter()

        lifecycleScope.launch {
            homeViewModel.topMoviesData.collect {
                topMoviesAdapter.addList(it.items)
            }
        }

    }


    fun createAdapter() {
        topMoviesAdapter = TopMoviesAdapter()
        binding.apply {
            topMoviesRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            topMoviesRecyclerView.adapter = topMoviesAdapter

            boxOfficeRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            boxOfficeRecyclerView.adapter = topMoviesAdapter
            comingSoonRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            comingSoonRecyclerView.adapter = topMoviesAdapter
            inTheatersRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            inTheatersRecyclerView.adapter = topMoviesAdapter
        }

    }
}