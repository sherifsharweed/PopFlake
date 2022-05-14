package com.shekoo.popflake.model.api

import com.shekoo.popflake.model.entities.Movies
import com.shekoo.popflake.model.entities.Search
import com.shekoo.popflake.model.entities.Trailer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET("en/API/Top250Movies/k_uzo2jq5u")
   suspend fun getTopMovies() : Response<Movies>

   @GET("en/API/ComingSoon/k_uzo2jq5u")
   suspend fun getComingSoon() : Response<Movies>

   @GET("en/API/InTheaters/k_uzo2jq5u")
   suspend fun getInTheaters() : Response<Movies>

   @GET("en/API/BoxOffice/k_uzo2jq5u")
   suspend fun getBoxOffice() : Response<Movies>

   @GET("en/API/SearchTitle/k_uzo2jq5u/{title}")
   suspend fun getSearch(@Path("title") title:String ): Response<Search>

   @GET("en/API/Trailer/k_uzo2jq5u/{id}")
   suspend fun getTrailer(@Path("id") id:String) : Response<Trailer>

}