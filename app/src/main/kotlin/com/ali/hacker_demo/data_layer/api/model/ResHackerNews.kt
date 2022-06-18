package com.ali.hacker_demo.data_layer.api.model

data class ResHackerNews(
    val by: String = "",
    val descendants: Int = 0,
    val id: Long = -1,
    val kids: List<Long>,
    val score: Long = 0,
    val time: Long,
    val title: String = "",
    val type: String = "",
    val url: String = "",
    var text: String? = ""
)