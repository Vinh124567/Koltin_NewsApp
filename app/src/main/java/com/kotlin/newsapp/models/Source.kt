//package com.kotlin.newsapp.models
//
//data class Source(
//    val id: String?,
//    val name: String?
//)
package com.kotlin.newsapp.models

data class Source(
    val id: String? = null,
    val name: String? = null
) {
    constructor() : this("", "") // Thêm constructor không đối số
}
