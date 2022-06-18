package com.ali.hacker_demo.common.model

data class NewsDetailCache(
    val id: Long,
    val title: String,
    val type: String,
    val author: String,
    val date: String,
    val description: String,
    val kids: Int = 0,
    val score: Long = 0
) {
    var comments: List<String> = ArrayList()
    var isCommentsIsLoaded = false
}
