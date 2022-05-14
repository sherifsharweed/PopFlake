package com.shekoo.popflake.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.shekoo.popflake.model.entities.Movies
import com.shekoo.popflake.model.data.RemoteDataSource
import com.shekoo.popflake.model.entities.Items
import com.shekoo.popflake.model.entities.Trailer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    ViewModel() {

    //Top Movies
    private val _topMoviesData = MutableStateFlow<Movies>(Movies(listOf(), ""))
    val topMoviesData: Flow<Movies> = _topMoviesData

    //Coming Soon
    private val _comingSoonData = MutableStateFlow<Movies>(Movies(listOf(), ""))
    val comingSoonData: Flow<Movies> = _comingSoonData

    //InTheaters
    private val _inTheatersData = MutableStateFlow<Movies>(Movies(listOf(), ""))
    val inTheatersData: Flow<Movies> = _inTheatersData

    //BoxOffice
    private val _boxOfficeData = MutableStateFlow<Movies>(Movies(listOf(), ""))
    val boxOfficeData: Flow<Movies> = _boxOfficeData

    //Trailer
    private var _trailerData = MutableStateFlow<MutableList<Trailer>>(mutableListOf<Trailer>())
    var trailerData: Flow<MutableList<Trailer>> = _trailerData


    //Error
    private val _error = MutableStateFlow<String>("")
    val error: Flow<String> = _error

    //Loading
    var loadingData = MutableStateFlow<Boolean>(false)

    init {
        /*getTopMovies()
        getComingSoon()
        getInTheaters()
        getBoxOffice()*/

    }

    fun getTopMovies() {
        viewModelScope.launch {
            try {
                loadingData.value = true
                _topMoviesData.value = remoteDataSource.getTopMovies()
                loadingData.value = false
            } catch (e: Exception) {
                _error.value = e.message ?: "Error!"
                loadingData.value = false
            }

        }
    }

    fun getComingSoon() {
        viewModelScope.launch {
            try {
                _comingSoonData.value = remoteDataSource.getComingSoon()

            } catch (e: Exception) {
                _error.value = e.message ?: "Error!"
            }
        }
    }

    fun getInTheaters() {
        viewModelScope.launch {
            try {
                _trailerData.value.clear()
                _inTheatersData.value = remoteDataSource.getInTheaters()
                sendIdForTrailer(_inTheatersData.value.items!!)
            } catch (e: Exception) {
                _error.value = e.message ?: "Error!"
            }
        }
    }

    private fun sendIdForTrailer(items: List<Items>) {
        val list = mutableListOf<Trailer>()
        runBlocking  {
        for(i in items){
                val trailer = remoteDataSource.getTrailer(i.id?:"")
                list.add(trailer)
            }
        }
        _trailerData.value = list

    }

    fun getBoxOffice() {
        viewModelScope.launch {
            try {
                _boxOfficeData.value = remoteDataSource.getBoxOffice()
            } catch (e: Exception) {
                _error.value = e.message ?: "Error!"
            }
        }
    }


}