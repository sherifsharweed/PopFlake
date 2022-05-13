package com.shekoo.popflake.model.api

import com.shekoo.popflake.model.entities.Movies
import com.shekoo.popflake.model.entities.Search
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET("en/API/Top250Movies/k_oxjimee0")
   suspend fun getTopMovies() : Response<Movies>

   @GET("en/API/ComingSoon/k_oxjimee0")
   suspend fun getComingSoon() : Response<Movies>

   @GET("en/API/InTheaters/k_oxjimee0")
   suspend fun getInTheaters() : Response<Movies>

   @GET("en/API/BoxOffice/k_oxjimee0")
   suspend fun getBoxOffice() : Response<Movies>

   @GET("en/API/SearchTitle/k_oxjimee0/{title}")
   suspend fun getSearch(@Path("title") title:String ):
           Response<Search>

}