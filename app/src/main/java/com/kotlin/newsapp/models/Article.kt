package com.kotlin.newsapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kotlin.newsapp.db.ARTICLES_TABLE
import java.io.Serializable

@Entity(tableName = ARTICLES_TABLE)
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = 0,
    var author: String? = "",
    var content: String? = "",
    var description: String? = "",
    var publishedAt: String? = "",
    var source: Source? = null,
    var title: String? = "",
    var url: String? = "",
    var urlToImage: String? = ""
) : Serializable {
    constructor() : this(0, "", "", "", "", null, "", "", "") // Thêm constructor không đối số
}
