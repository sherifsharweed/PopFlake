package com.shekoo.popflake.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.shekoo.popflake.utilities.Network
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var topMoviesAdapter: AdapterTopMovies
    private lateinit var comingSoonAdapter: AdapterComingSoon
    private lateinit var inTheaterAdapter: AdapterInTheaters
    private lateinit var boxOfficeAdapter: AdapterBoxOffice

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        if (!Network.hasInternet(requireContext())) {
            Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createAdapter()

        lifecycleScope.launchWhenStarted {
            homeViewModel.topMoviesData.collect {
                topMoviesAdapter.addList(it.items ?: emptyList())
            }
        }

        lifecycleScope.launchWhenStarted {
            homeViewModel.comingSoonData.collect {
                comingSoonAdapter.addList(it.items ?: emptyList())
            }
        }

        lifecycleScope.launchWhenStarted {
            homeViewModel.boxOfficeData.collect {
                boxOfficeAdapter.addList(it.items ?: emptyList())
            }
        }

        lifecycleScope.launchWhenStarted {
            homeViewModel.inTheatersData.collect {
                inTheaterAdapter.addList(it.items ?: emptyList())
            }
        }

        lifecycleScope.launchWhenStarted {
            homeViewModel.topMoviesError.collect {
                if (it != "") {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        binding.apply {
            topMoviesTextView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/chart/top"))
                startUrlActivity(intent)
            }
            comingSoonTextView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/movies-coming-soon"))
                startUrlActivity(intent)
            }
            inTheatersTextView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/movies-in-theaters"))
                startUrlActivity(intent)
            }
            boxOfficeTextView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/chart/boxoffice"))
                startUrlActivity(intent)

            }
            refreshLayout.setOnRefreshListener {
                homeViewModel.apply {
                    /*getTopMovies()
                    getBoxOffice()
                    getInTheaters()
                    getComingSoon()*/
                }
                refreshLayout.isRefreshing = false
            }
        }
    }

    private fun createAdapter() {
        topMoviesAdapter = AdapterTopMovies(this::startUrlActivity)
        comingSoonAdapter = AdapterComingSoon(this::startUrlActivity)
        inTheaterAdapter = AdapterInTheaters(this::startUrlActivity)
        boxOfficeAdapter = AdapterBoxOffice(this::startUrlActivity)

        binding.apply {

            topMoviesRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            topMoviesRecyclerView.adapter = topMoviesAdapter

            comingSoonRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            comingSoonRecyclerView.adapter = comingSoonAdapter

            inTheatersRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            inTheatersRecyclerView.adapter = inTheaterAdapter

            boxOfficeRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            boxOfficeRecyclerView.adapter = boxOfficeAdapter
        }

    }

    private fun startUrlActivity(intent: Intent) {
        startActivity(intent)
    }
}