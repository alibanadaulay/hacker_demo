package com.ali.hacker_demo.domain

import com.ali.hacker_demo.common.UsesCaseResult
import com.ali.hacker_demo.data_layer.api.IHackerNew
import com.ali.hacker_demo.common.model.NewsCache
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface GetTopStoryUsesCase {
    operator fun invoke(): Flow<UsesCaseResult<List<NewsCache>>>
}

class GetTopStoryUsesCaseImpl @Inject constructor(private val iHackerNew: IHackerNew) :
    GetTopStoryUsesCase {
    override fun invoke(): Flow<UsesCaseResult<List<NewsCache>>> = flow {
        val response = iHackerNew.getItemsTopStories()
        var count = 1
        val result: MutableList<NewsCache> = ArrayList()
        if (response.isSuccessful) {
            val topStoriesId = response.body() ?: ArrayList()
            topStoriesId.forEach {
                val responseItem = iHackerNew.getItemById(it)
                result.add(
                    NewsCache(
                        id = responseItem.id,
                        title = responseItem.title,
                        descendants = responseItem.descendants,
                        score = responseItem.score
                    )
                )
                if (result.size > count * 20 || result.size == topStoriesId.size) {
                    emit(UsesCaseResult.OnSuccess(result))
                    count++
                }
            }
        } else {
            emit(UsesCaseResult.OnError(Throwable(response.errorBody()?.string() ?: "")))
        }

    }

}