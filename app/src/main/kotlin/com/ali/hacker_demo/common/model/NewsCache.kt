package com.ali.hacker_demo.common.model

data class NewsCache(
    val id: Long = -1,
    val title: String = "",
    val descendants: Int = 0,
    val score: Long = 0
)