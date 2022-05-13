package com.shekoo.popflake.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shekoo.popflake.model.data.RemoteDataSource
import com.shekoo.popflake.model.entities.Movies
import com.shekoo.popflake.model.entities.Search
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val remoteDataSource: RemoteDataSource) : ViewModel() {

    //Search
    private val _searchData = MutableStateFlow<Search>(Search("","", emptyList(),""))
    val searchData: Flow<Search> = _searchData

    //Error
    private val _searchError = MutableStateFlow<String>("")
    val searchError: Flow<String> = _searchError

    //Loading
    var loadingData = MutableStateFlow<Boolean>(false)


    fun getSearch(title : String){
        viewModelScope.launch {
            try {
                if (remoteDataSource.getSearch(title) != null) {
                    loadingData.value = true
                    _searchData.value = remoteDataSource.getSearch(title)!!
                    loadingData.value = false
                } else {
                    _searchError.value ="No Connection"
                }
            }catch (e: Exception){
                _searchError.value = e.message?:"Error!"
            }
        }
    }
}