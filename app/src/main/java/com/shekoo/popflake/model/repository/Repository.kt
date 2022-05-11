package com.shekoo.popflake.model.repository

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.shekoo.popflake.MyApplication
import com.shekoo.popflake.model.api.ApiServices
import com.shekoo.popflake.model.entities.TopMovies
import com.shekoo.popflake.utilities.Either
import com.shekoo.popflake.utilities.Errors
import com.shekoo.popflake.utilities.Network.hasInternet
import retrofit2.Response

class Repository(
    private val apiServices: ApiServices,
) {

    /*suspend fun getTopMovies() : Either<TopMovies,Errors> {

        return try {
            return if (hasInternet(application.applicationContext)) {
                val res = apiServices.getTopMovies()
                if (res.isSuccessful) {
                    Either.Success(res.body()!!)
                } else {
                    Either.Error(Errors.ServerError)
                }
            } else {
                Either.Error(Errors.NoInternetConnection)
            }
        } catch (t: Throwable) {
            Either.Error(Errors.ServerError)
        }
    }*/

    suspend fun getTopMovies() : TopMovies? {
        val res = apiServices.getTopMovies()
        return if (res.isSuccessful) {
            res.body()!!
        }else{
            null
        }
    }
}