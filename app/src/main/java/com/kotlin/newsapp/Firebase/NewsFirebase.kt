package com.kotlin.newsapp.Firebase

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kotlin.newsapp.models.Article
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NewsFirebase constructor(context: Context) {
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    private val firebaseAuth = FirebaseAuth.getInstance()
    companion object {
        private var instance: NewsFirebase? = null
        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            instance ?: NewsFirebase(context).also { instance = it }
        }
    }


    suspend fun addTofavorites(article: Article): Boolean {
        val articleReference = databaseReference.child("FavoriteArticle")
            .child(getCurrentUserUid().toString())
        val newArticleReference = articleReference.child(article.publishedAt.toString())
        var success = false // Biến để lưu trạng thái thao tác ghi

        try {
            newArticleReference.setValue(article).await() // Sử dụng await để chờ thao tác ghi hoàn thành
            println("Bản ghi đã được ghi thành công!")
            success = true
        } catch (e: Exception) {
            println("Lỗi: $e")
        }

        return success
    }

    suspend fun isExistsFavoriteNews(article: Article): Boolean {
        val articleReference = databaseReference.child("FavoriteArticle")
            .child(getCurrentUserUid().toString())

        return suspendCoroutine { continuation ->
            articleReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.child(article.publishedAt.toString()).exists()) {
                        continuation.resume(true)
                    } else {
                        continuation.resume(false)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("Đã xảy ra lỗi: " + databaseError.message)
                    continuation.resume(false)
                }
            })
        }
    }

    fun loadFavourite(favoriteList: MutableLiveData<List<Article>>) {
        databaseReference.child("FavoriteArticle").child(getCurrentUserUid().toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val articles = mutableListOf<Article>()
                for (articleSnapshot in dataSnapshot.children) {
                    val article = articleSnapshot.getValue(Article::class.java)
                    article?.let {
                        articles.add(it)
                    }
                }
                favoriteList.postValue(articles)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                favoriteList.postValue(null)
            }
        })
    }

    suspend fun removeFavoriteNews(article: Article) {
        val articleReference = databaseReference.child("FavoriteArticle")
            .child(getCurrentUserUid().toString())
            .child(article.publishedAt.toString())

        try {
            // Xóa bài báo khỏi danh sách yêu thích
            articleReference.removeValue().await()
            println("Đã xóa bài báo thành công")
        } catch (e: Exception) {
            println("Đã xảy ra lỗi khi xóa bài báo: ${e.message}")
        }
    }



    fun addToHistory(article: Article) {
        val articleReference = databaseReference.child("TheNews").child(getCurrentUserUid().toString()).push()
        articleReference.setValue(article)
    }

    fun loadHistory(articlesList: MutableLiveData<List<Article>>) {
        databaseReference.child("TheNews").child(getCurrentUserUid().toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val articles = mutableListOf<Article>()
                for (articleSnapshot in dataSnapshot.children) {
                        val article = articleSnapshot.getValue(Article::class.java)
                        article?.let {
                            articles.add(it)
                        }
                }
                articlesList.postValue(articles)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                articlesList.postValue(null)
            }
        })
    }

    fun loginWithEmail(email: String, password: String, callback: (Boolean) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
    }

    fun registerWithEmail(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = firebaseAuth.currentUser?.uid
                    callback(true, uid)
                } else {
                    callback(false, null)
                }
            }
    }
    fun getCurrentUserUid(): String? {
        return firebaseAuth.currentUser?.uid
    }

}


