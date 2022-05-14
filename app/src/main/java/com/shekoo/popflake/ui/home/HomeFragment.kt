package com.shekoo.popflake.ui.home

import android.app.AlertDialog
import android.app.ProgressDialog
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
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.shekoo.popflake.databinding.FragmentHomeBinding
import com.shekoo.popflake.model.entities.ImageSlider
import com.shekoo.popflake.utilities.Constants.TAG
import com.shekoo.popflake.utilities.Network
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {

    //Adapters
    private lateinit var binding: FragmentHomeBinding
    private lateinit var topMoviesAdapter: AdapterTopMovies
    private lateinit var comingSoonAdapter: AdapterComingSoon
    private lateinit var inTheaterAdapter: AdapterInTheaters
    private lateinit var boxOfficeAdapter: AdapterBoxOffice

    //For image slider
    private var listOfImageForSlider: MutableList<ImageSlider> = mutableListOf()

    private var dialog: AlertDialog? = null

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
            homeViewModel.inTheatersData.collect {
                inTheaterAdapter.addList(it.items ?: emptyList())
            }
        }

        lifecycleScope.launchWhenStarted {
            homeViewModel.boxOfficeData.collect {
                boxOfficeAdapter.addList(it.items ?: emptyList())

            }
        }

        lifecycleScope.launchWhenStarted {
            homeViewModel.trailerData.collectLatest {
                listOfImageForSlider.clear()
                for (i in it) {
                    listOfImageForSlider.add(ImageSlider(i.imDbId, i.title, i.thumbnailUrl, i.link))
                }
                addSlider()
            }
        }

        lifecycleScope.launchWhenStarted {
            homeViewModel.loadingData.collectLatest {
                if (it) showLoading() else hideLoading()
            }
        }

        lifecycleScope.launchWhenStarted {
            homeViewModel.error.collect {
                if (it != "") {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }


        return binding.root
    }

    override fun onResume() {
        super.onResume()


        binding.apply {
            topMoviesTextView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/chart/top"))
                startActivity(intent)
            }
            comingSoonTextView.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/movies-coming-soon"))
                startActivity(intent)
            }
            inTheatersTextView.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/movies-in-theaters"))
                startActivity(intent)
            }
            boxOfficeTextView.setOnClickListener {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/chart/boxoffice"))
                startActivity(intent)

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
        topMoviesAdapter = AdapterTopMovies()
        comingSoonAdapter = AdapterComingSoon()
        inTheaterAdapter = AdapterInTheaters()
        boxOfficeAdapter = AdapterBoxOffice()

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

    private fun addSlider() {
        val imageList = ArrayList<SlideModel>()
        for (i in listOfImageForSlider) {
            if (i.id != "") {
                imageList.add(
                    SlideModel(
                        i.poster,
                        i.title,
                        ScaleTypes.CENTER_CROP
                    )
                )
            }
        }
        binding.imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                val url = listOfImageForSlider[position].videoUrl
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                requireActivity().startActivity(intent)
            }

        })

    }

    private fun showLoading() {
        dialog = ProgressDialog.show(requireContext(), "", "Loading. Please wait...", true);
    }

    private fun hideLoading() {
        dialog?.dismiss()
    }

}