package com.kotlin.newsapp.repository.remote

import com.kotlin.newsapp.api.RetrofitInstance
import com.kotlin.newsapp.models.Article

class NewsRemoteRepository {

    suspend fun getHeadlines(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getHeadlines()

    suspend fun search(query: String, pageNumber: Int) =
        RetrofitInstance.api.search(query)

    suspend fun update(id: Int, article: Article) =
        RetrofitInstance.api.updateArticle(id,article)

}