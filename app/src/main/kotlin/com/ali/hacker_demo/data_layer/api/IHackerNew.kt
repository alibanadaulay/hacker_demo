package com.ali.hacker_demo.data_layer.api

import com.ali.hacker_demo.data_layer.api.model.ResHackerNews
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IHackerNew {
    @GET("item/{id}.json")
    suspend fun getItemById(@Path("id") id: Long): ResHackerNews

    @GET("topstories.json")
    suspend fun getItemsTopStories(): Response<List<Long>>
}