package com.kotlin.newsapp.api

import com.kotlin.newsapp.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("custom/read.php")
    suspend fun getHeadlines(
    ): Response<NewsResponse>

    @GET("custom/search.php")
    suspend fun search(
        @Query("q")
        searchQuery: String = ""
    ): Response<NewsResponse>
}