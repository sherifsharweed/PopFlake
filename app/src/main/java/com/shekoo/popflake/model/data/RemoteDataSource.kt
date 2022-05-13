package com.shekoo.popflake.model.data

import android.util.Log
import com.shekoo.popflake.model.api.ApiServices
import com.shekoo.popflake.model.entities.Movies
import com.shekoo.popflake.model.entities.Search
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiServices: ApiServices) {

    suspend fun getTopMovies(): Movies? {
        val res = apiServices.getTopMovies()
        return if (res.isSuccessful) {
            res.body()!!
        } else {
            null
        }
    }

    suspend fun getComingSoon(): Movies? {
        val result = apiServices.getComingSoon()
        return if (result.isSuccessful) {
            result.body()!!
        } else {
            null
        }
    }

    suspend fun getInTheaters() : Movies?{
        val result = apiServices.getInTheaters()
        return if (result.isSuccessful){
            result.body()
        }else{
            null
        }
    }

    suspend fun getBoxOffice() : Movies?{
        val result = apiServices.getBoxOffice()
        return if (result.isSuccessful){
            result.body()
        }else{
            null
        }
    }

    suspend fun getSearch(title : String) : Search?{
        val result = apiServices.getSearch(title)
        return if (result.isSuccessful){
            result.body()
        }else{
            null
        }
    }
}