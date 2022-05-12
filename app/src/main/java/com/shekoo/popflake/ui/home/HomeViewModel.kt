package com.shekoo.popflake.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.shekoo.popflake.model.entities.TopMovies
import com.shekoo.popflake.model.data.RemoteDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val remoteDataSource: RemoteDataSource) : ViewModel() {

    private val _topMoviesData = MutableStateFlow<TopMovies>(TopMovies(listOf(), ""))
    val topMoviesData: Flow<TopMovies> = _topMoviesData
    private val _topMoviesError = MutableStateFlow<String>("")
    val topMoviesError: Flow<String> = _topMoviesError

    init {
        getTopMovies()
    }

    private fun getTopMovies() {
        viewModelScope.launch {
            try {
                if (remoteDataSource.getTopMovies() != null) {
                    _topMoviesData.value = remoteDataSource.getTopMovies()!!
                    Log.i("TAG", "getTopMovies: ")
                } else {
                    _topMoviesError.value ="No Connection"
                }
            }catch (e: Exception){
                _topMoviesError.value = e.message?:"Error!"
            }

        }
    }

}