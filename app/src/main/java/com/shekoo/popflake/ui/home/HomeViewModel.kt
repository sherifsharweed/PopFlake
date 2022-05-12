package com.shekoo.popflake.ui.home

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.shekoo.popflake.MyApplication
import com.shekoo.popflake.model.api.ImdbApi
import com.shekoo.popflake.model.entities.TopMovies
import com.shekoo.popflake.model.repository.Repository
import com.shekoo.popflake.utilities.Either
import com.shekoo.popflake.utilities.Errors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class HomeViewModel : ViewModel() {

    private val repository : Repository = Repository(ImdbApi.getApiService())

    private val _topMoviesData = MutableStateFlow<TopMovies>(TopMovies(listOf(),""))
    val topMoviesData: Flow<TopMovies> = _topMoviesData
    private val _topMoviesError = MutableStateFlow<Errors>(Errors.NullValue)
    val topMoviesError: Flow<Errors> = _topMoviesError



    init {
        getTopMovies()
    }

    fun getTopMovies() {
        viewModelScope.launch {
            if (repository.getTopMovies()!=null){
                _topMoviesData.value = repository.getTopMovies()!!
                Log.i("TAG", "getTopMovies: ")
            }else{
                _topMoviesError.value= Errors.valueOf("No Connection")
            }
        }
    }

}