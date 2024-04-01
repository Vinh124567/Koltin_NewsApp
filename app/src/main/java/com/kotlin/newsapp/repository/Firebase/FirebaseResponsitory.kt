package com.kotlin.newsapp.repository.Firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kotlin.newsapp.Firebase.NewsFirebase
import com.kotlin.newsapp.models.Article
import kotlinx.coroutines.launch

class FirebaseResponsitory (  private val firebase:NewsFirebase){

    fun loginWithEmail(email: String, password: String, callback: (Boolean) -> Unit){
        firebase.loginWithEmail(email, password, callback)
    }
    fun registerWithEmail(email: String, password: String, callback: (Boolean, String?) -> Unit){
        firebase.registerWithEmail(email,password,callback)
    }

    suspend fun isExistsFavariteNews(article: Article)=firebase.isExistsFavariteNews(article)
    suspend fun addToFavorite(article: Article)=firebase.addTofavorites(article);
    fun addToHistory(article:Article)=firebase.addToHistory(article)
    fun loadHistory(articlesList: MutableLiveData<List<Article>>)=firebase.loadHistory(articlesList)
}

//fun addToFavorites(article: Article) = viewModelScope.launch {
//    if (firebaseResponsitory.addToFavorite(article)) {
//        updateArticleSaveStatus(true)
//    }else {
//        updateArticleSaveStatus(false)
//    }
//}
//
//fun isExistsFavoriteNews(article: Article)=viewModelScope.launch {
//    if(firebaseResponsitory.isExistsFavariteNews(article)){
//        updateArticleSaveStatus(true);
//    }
//}
//    fun registerWithEmail(email: String, password: String, callback: (Boolean, String?) -> Unit){
//        firebaseResponsitory.registerWithEmail(email,password,callback)
//    }
//fun addToHistory(article: Article){
//    firebaseResponsitory.addToHistory(article)
//}



