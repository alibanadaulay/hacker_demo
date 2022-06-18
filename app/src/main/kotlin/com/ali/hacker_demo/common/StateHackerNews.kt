package com.ali.hacker_demo.common

import com.ali.hacker_demo.common.model.NewsCache
import com.ali.hacker_demo.common.model.NewsDetailCache

sealed class StateHackerNews {
    object Idle : StateHackerNews()
    object GetDataOnProgress : StateHackerNews()
    object NewsOnSelected : StateHackerNews()
    data class ShowFavorite(val hackerNews: NewsCache, val isShow: Boolean) : StateHackerNews()
    data class GetDataNewHackerSuccess(val hackerNews: List<NewsCache>) : StateHackerNews()
    data class GetDetailNew(val newsDetailCache: NewsDetailCache, val isFavorite: Boolean) :
        StateHackerNews()
    data class OnError(val throwable: Throwable) : StateHackerNews()
}
