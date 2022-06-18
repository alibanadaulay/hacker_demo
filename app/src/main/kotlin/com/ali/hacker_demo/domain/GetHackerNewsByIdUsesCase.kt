package com.ali.hacker_demo.domain

import com.ali.hacker_demo.common.UsesCaseResult
import com.ali.hacker_demo.data_layer.api.IHackerNew
import com.ali.hacker_demo.common.model.NewsDetailCache
import com.ali.hacker_demo.extensions.toDateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetHackerNewsByIdUsesCase {
    operator fun invoke(id: Long): Flow<UsesCaseResult<NewsDetailCache>>
}

class GetHackerNewByIdUsesCaseIml @Inject constructor(private val hackerNew: IHackerNew) :
    GetHackerNewsByIdUsesCase {
    override fun invoke(id: Long): Flow<UsesCaseResult<NewsDetailCache>> = flow {
        try {
            val result = hackerNew.getItemById(id)
            val newsDetailCache = NewsDetailCache(
                id = result.id,
                title = result.title,
                type = result.type,
                author = result.by,
                date = result.time.toDateUtils(),
                description = result.text ?: "",
                kids = result.descendants,
                score = result.score
            )
            emit(UsesCaseResult.OnSuccess(newsDetailCache))
            val comments: MutableList<String> = mutableListOf()
            var count = result.kids.size
            result.kids.forEach {
                val item = hackerNew.getItemById(it)
                count--
                if (item.text != null) {
                    comments.add(item.text ?: "")
                }
                if (count <= 0) {
                    newsDetailCache.isCommentsIsLoaded = true
                    newsDetailCache.comments = comments
                    emit(UsesCaseResult.OnSuccess(newsDetailCache))
                }
            }

        } catch (e: Exception) {
            emit(UsesCaseResult.OnError(e))
        }
    }
}