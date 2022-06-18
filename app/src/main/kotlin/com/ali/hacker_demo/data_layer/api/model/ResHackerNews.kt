package com.ali.hacker_demo.data_layer.api.model

data class ResHackerNews(
    val by: String,
    val descendants: Int,
    val id: Long,
    val kids: List<Long>,
    val score: Long,
    val time: Long,
    val title: String,
    val type: String,
    val url: String,
    var text:String? = ""
)