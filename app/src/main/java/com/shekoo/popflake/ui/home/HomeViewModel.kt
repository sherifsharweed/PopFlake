package com.shekoo.popflake.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.shekoo.popflake.model.entities.Movies
import com.shekoo.popflake.model.data.RemoteDataSource
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

    //Error
    private val _topMoviesError = MutableStateFlow<String>("")
    val topMoviesError: Flow<String> = _topMoviesError

    init {
        getTopMovies()
        getComingSoon()
        getInTheaters()
        getBoxOffice()
    }

    private fun getTopMovies() {
        viewModelScope.launch {
            try {
                if (remoteDataSource.getTopMovies() != null) {
                    _topMoviesData.value = remoteDataSource.getTopMovies()!!
                } else {
                    _topMoviesError.value ="No Connection"
                }
            }catch (e: Exception){
                _topMoviesError.value = e.message?:"Error!"
            }

        }
    }

    private fun getComingSoon() {
        viewModelScope.launch {
            try {
                if (remoteDataSource.getComingSoon() != null) {
                    _comingSoonData.value = remoteDataSource.getComingSoon()!!
                } else {
                    _topMoviesError.value ="No Connection"
                }
            }catch (e: Exception){
                _topMoviesError.value = e.message?:"Error!"
            }
        }
    }

    private fun getInTheaters(){
        viewModelScope.launch {
            try{
                if(remoteDataSource.getInTheaters() != null){
                    _inTheatersData.value = remoteDataSource.getInTheaters()!!
                }else{
                    _topMoviesError.value ="No Connection"
                }
            }catch (e: Exception){
                _topMoviesError.value = e.message?:"Error!"
            }
        }
    }

    private fun getBoxOffice(){
        viewModelScope.launch {
            try{
                if(remoteDataSource.getBoxOffice() != null){
                    _boxOfficeData.value = remoteDataSource.getBoxOffice()!!
                }else{
                    _topMoviesError.value ="No Connection"
                }
            }catch (e: Exception){
                _topMoviesError.value = e.message?:"Error!"
            }
        }
    }
}