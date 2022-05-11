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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shekoo.popflake.MyApplication
import com.shekoo.popflake.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val topMoviesRecyclerView: RecyclerView by lazy { binding.topMoviesRecyclerView }
    private val boxOfficeRecyclerView: RecyclerView by lazy { binding.boxOfficeRecyclerView }
    private val comingSoonRecyclerView: RecyclerView by lazy { binding.comingSoonRecyclerView }
    private val inTheatersRecyclerView: RecyclerView by lazy { binding.inTheatersRecyclerView }

    private lateinit var topMoviesAdapter : TopMoviesAdapter

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        Log.i("TAG", "onCreateView: ")
        homeViewModel.getTopMovies()

        lifecycleScope.launch{
            homeViewModel.topMoviesData.collectLatest {
                topMoviesAdapter = TopMoviesAdapter(it)
                topMoviesRecyclerView.layoutManager =
                    LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
                topMoviesRecyclerView.adapter = topMoviesAdapter

                boxOfficeRecyclerView.layoutManager =
                    LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
                boxOfficeRecyclerView.adapter = topMoviesAdapter
                comingSoonRecyclerView.layoutManager =
                    LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
                comingSoonRecyclerView.adapter = topMoviesAdapter
                inTheatersRecyclerView.layoutManager =
                    LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
                inTheatersRecyclerView.adapter = topMoviesAdapter

            }

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}