package com.kotlin.newsapp.api

import com.kotlin.newsapp.models.Article
import com.kotlin.newsapp.models.NewsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsAPI {
    @GET("custom/getArticle.php")
    suspend fun getHeadlines(
    ): Response<NewsResponse>

    @GET("custom/searchArticle.php")
    suspend fun search(
        @Query("q")
        searchQuery: String = ""
    ): Response<NewsResponse>

    @PUT("custom/updateArticle.php/{id}")
    suspend fun updateArticle(
        @Path("id") id: Int,
        @Body article: Article
    )

}