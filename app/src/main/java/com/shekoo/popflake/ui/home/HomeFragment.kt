package com.shekoo.popflake.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shekoo.popflake.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var topMoviesAdapter: TopMoviesAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createAdapter()
        lifecycleScope.launch {
            homeViewModel.topMoviesData.collect {
                topMoviesAdapter.addList(it.items ?: emptyList())
            }
            
            homeViewModel.topMoviesError.collect{
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun createAdapter() {
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