package com.shekoo.popflake.model.api

import android.provider.SyncStateContract
import com.shekoo.popflake.model.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ImdbApi {

    private fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApiService(): ApiServices {
        return getInstance().create(ApiServices::class.java)
    }

}