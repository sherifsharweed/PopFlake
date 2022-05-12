package com.shekoo.popflake.model.api

import com.shekoo.popflake.model.entities.Movies
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {

    @GET("en/API/Top250Movies/k_ssi5bndp")
   suspend fun getTopMovies() : Response<Movies>

   @GET("en/API/ComingSoon/k_ssi5bndp")
   suspend fun getComingSoon() : Response<Movies>

}