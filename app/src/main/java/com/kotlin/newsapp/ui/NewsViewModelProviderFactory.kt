package com.kotlin.newsapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kotlin.newsapp.repository.remote.NewsRemoteRepository

class NewsViewModelProviderFactory(
    val app: Application,
    private val newsRemoteRepository: NewsRemoteRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, newsRemoteRepository) as T
    }
}