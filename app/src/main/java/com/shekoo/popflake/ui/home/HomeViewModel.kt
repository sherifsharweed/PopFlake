package com.shekoo.popflake.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shekoo.popflake.model.data.RemoteDataSource
import com.shekoo.popflake.model.entities.Items
import com.shekoo.popflake.model.entities.Movies
import com.shekoo.popflake.model.entities.Trailer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
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
        getTopMovies()
        getComingSoon()
        getInTheaters()
        getBoxOffice()

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
                val process = async { sendIdForTrailer(_inTheatersData.value.items!!) }
                _trailerData.value = process.await()
            } catch (e: Exception) {
                _error.value = e.message ?: "Error!"
            }
        }
    }

    private suspend fun sendIdForTrailer(items: List<Items>): MutableList<Trailer> {
        val list = mutableListOf<Trailer>()
        for (i in items) {
            i.id?.let {
                val trailer = remoteDataSource.getTrailer(it)
                list.add(trailer)
            }
        }
        return list
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