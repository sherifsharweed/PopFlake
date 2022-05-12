package com.shekoo.popflake.model.data

import com.shekoo.popflake.model.api.ApiServices
import com.shekoo.popflake.model.entities.TopMovies
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiServices: ApiServices) {

    suspend fun getTopMovies(): TopMovies? {
        val res = apiServices.getTopMovies()
        return if (res.isSuccessful) {
            res.body()!!
        } else {
            null
        }
    }
}