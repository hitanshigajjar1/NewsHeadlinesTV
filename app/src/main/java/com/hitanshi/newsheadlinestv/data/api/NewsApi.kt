package com.hitanshi.newsheadlinestv.data.api

import com.hitanshi.newsheadlinestv.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getNews(

        @Query("country")
        country: String,

        @Query("category")
        category: String,

        @Query("pageSize")
        pageSize: Int,

        @Query("apiKey")
        apiKey: String

    ): NewsResponse

}