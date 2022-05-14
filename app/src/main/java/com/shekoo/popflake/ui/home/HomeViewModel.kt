package com.shekoo.popflake.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.shekoo.popflake.model.entities.Movies
import com.shekoo.popflake.model.data.RemoteDataSource
import com.shekoo.popflake.model.entities.Trailer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val remoteDataSource: RemoteDataSource) : ViewModel() {

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
    private val _trailerData = MutableStateFlow<Trailer>(Trailer("","","","",""))
    val trailerData: Flow<Trailer> = _trailerData

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
                if (remoteDataSource.getTopMovies() != null) {
                    loadingData.value = true
                    _topMoviesData.value = remoteDataSource.getTopMovies()!!
                    loadingData.value = false
                } else {
                    _error.value ="No Connection"
                }
            }catch (e: Exception){
                _error.value = e.message?:"Error!"
            }

        }
    }

     fun getComingSoon() {
        viewModelScope.launch {
            try {
                if (remoteDataSource.getComingSoon() != null) {
                    _comingSoonData.value = remoteDataSource.getComingSoon()!!
                    Log.i("TAG", "getComingSoon: "+remoteDataSource.getComingSoon()!!)
                } else {
                    _error.value ="No Connection"
                }
            }catch (e: Exception){
                _error.value = e.message?:"Error!"
            }
        }
    }

     fun getInTheaters(){
        viewModelScope.launch {
            try{
                if(remoteDataSource.getInTheaters() != null){
                    _inTheatersData.value = remoteDataSource.getInTheaters()!!
                }else{
                    _error.value ="No Connection"
                }
            }catch (e: Exception){
                _error.value = e.message?:"Error!"
            }
        }
    }

     fun getBoxOffice(){
        viewModelScope.launch {
            try{
                if(remoteDataSource.getBoxOffice() != null){
                    _boxOfficeData.value = remoteDataSource.getBoxOffice()!!
                }else{
                    _error.value ="No Connection"
                }
            }catch (e: Exception){
                _error.value = e.message?:"Error!"
            }
        }
    }
    
    fun getTrailer(id : String){
        viewModelScope.launch {
            try {
                //loadingData.value = true
                _trailerData.value = remoteDataSource.getTrailer(id)
                //loadingData.value = false
            }catch (e: Exception){
                _error.value = e.message?:"Error"
                loadingData.value = false
            }
        }
    }
}