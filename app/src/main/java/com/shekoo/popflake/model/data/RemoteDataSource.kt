package com.shekoo.popflake.model.data

import android.util.Log
import com.shekoo.popflake.model.api.ApiServices
import com.shekoo.popflake.model.entities.Movies
import com.shekoo.popflake.model.entities.Search
import com.shekoo.popflake.model.entities.Trailer
import com.shekoo.popflake.utilities.Constants
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiServices: ApiServices) {

    suspend fun getTopMovies(): Movies {
        val res = apiServices.getTopMovies()
        return if (res.isSuccessful) {
            res.body()?: Movies(emptyList(),"Error")
        } else Movies(emptyList(),"Error")
    }

    suspend fun getComingSoon(): Movies {
        val result = apiServices.getComingSoon()
        return if (result.isSuccessful) {
            result.body()?: Movies(emptyList(),"Error")
        } else Movies(emptyList(),"Error")
    }

    suspend fun getInTheaters() : Movies{
        val result = apiServices.getInTheaters()
        return if (result.isSuccessful){
            result.body()?: Movies(emptyList(),"Error")
        }else Movies(emptyList(),"Error")
    }

    suspend fun getBoxOffice() : Movies{
        val result = apiServices.getBoxOffice()
        return if (result.isSuccessful){
            result.body()?: Movies(emptyList(),"Error")
        }else Movies(emptyList(),"Error")
    }

    suspend fun getSearch(title : String) : Search{
        val result = apiServices.getSearch(title)
        return if (result.isSuccessful){
            result.body()?:Search("","", emptyList(),"Error")
        }else Search("","", emptyList(),"Error")

    }

    suspend fun getTrailer(id : String) : Trailer{
        val result = apiServices.getTrailer(id)
        return if (result.isSuccessful){
            result.body()?:Trailer("","","","","Error")
        }else Trailer("","","","","Error")
    }
}