package com.ali.hacker_demo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ali.hacker_demo.common.StateHackerNews
import com.ali.hacker_demo.common.UsesCaseResult
import com.ali.hacker_demo.common.model.NewsCache
import com.ali.hacker_demo.common.model.NewsDetailCache
import com.ali.hacker_demo.domain.GetHackerNewsByIdUsesCase
import com.ali.hacker_demo.domain.GetTopStoryUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTopStoryUsesCase: GetTopStoryUsesCase,
    private val getHackerNewsByIdUsesCase: GetHackerNewsByIdUsesCase
) :
    ViewModel() {
    private val _stateHacker = MutableStateFlow<StateHackerNews>(StateHackerNews.GetDataOnProgress)
    val viewState: Flow<StateHackerNews>
        get() = _stateHacker.asStateFlow().filterNotNull()

    private var mFavoriteNewsCache: NewsCache = NewsCache()
    private var mCurrentDetailCache: NewsDetailCache? = null
    private val newCacheItems: MutableList<NewsCache> = mutableListOf()
    private var job: Job? = null

    fun getTopStories() {
        viewModelScope.launch {
            newCacheItems.clear()
            _stateHacker.value = StateHackerNews.GetDataOnProgress
            getTopStoryUsesCase.invoke().collect {
                when (it) {
                    is UsesCaseResult.OnError -> {
                        _stateHacker.emit(StateHackerNews.OnError(it.err))
                    }
                    is UsesCaseResult.OnSuccess -> {
                        val items = it.value
                        if (items.size > newCacheItems.size) {
                            newCacheItems.addAll(items)
                        }
                        _stateHacker.emit(StateHackerNews.GetDataNewHackerSuccess(newCacheItems))
                    }
                }
            }
        }
    }

    fun saveFavorite(callback: (bool: Boolean) -> Unit) {
        viewModelScope.launch {
            mFavoriteNewsCache = if (mFavoriteNewsCache.id == mCurrentDetailCache?.id) {
                callback(false)
                NewsCache()
            } else {
                changeFavorite {
                    callback(it)
                }
            }
            _stateHacker.emit(
                StateHackerNews.ShowFavorite(
                    mFavoriteNewsCache,
                    mFavoriteNewsCache.id > 0
                )
            )
        }
    }

    fun makeStatIdle() {
        viewModelScope.launch {
            cancelJob()
            _stateHacker.emit(StateHackerNews.Idle)
        }
    }

    private fun changeFavorite(callback: (bool: Boolean) -> Unit): NewsCache {
        return if (checkIfCurrentDetailCacheIsNotNull()) {
            callback(true)
            NewsCache(
                id = mCurrentDetailCache!!.id,
                title = mCurrentDetailCache!!.title,
                descendants = mCurrentDetailCache!!.kids,
                score = mCurrentDetailCache!!.score
            )
        } else {
            callback(false)
            NewsCache()
        }
    }

    private fun checkIfCurrentDetailCacheIsNotNull() = mCurrentDetailCache != null

    private fun cancelJob() {
        if (job != null && job!!.isActive) {
            job!!.cancel()
        }
    }

    fun getNews(newsCache: NewsCache) {
        cancelJob()
        job = viewModelScope.launch {
            _stateHacker.emit(StateHackerNews.NewsOnSelected)
            if (mCurrentDetailCache != null && mCurrentDetailCache?.id == newsCache.id) {
                loadCurrentNewsCache()
            } else {
                getHackerNewById(newsCache.id)
            }
        }
    }

    private suspend fun loadCurrentNewsCache() {
        _stateHacker.emit(
            StateHackerNews.GetDetailNew(
                mCurrentDetailCache!!,
                mCurrentDetailCache?.id == mFavoriteNewsCache.id
            )
        )
    }

    private suspend fun
            getHackerNewById(id: Long) {
        getHackerNewsByIdUsesCase.invoke(id).collect {
            when (it) {
                is UsesCaseResult.OnError -> {
                    _stateHacker.emit(StateHackerNews.OnError(it.err))
                }
                is UsesCaseResult.OnSuccess -> {
                    mCurrentDetailCache = it.value
                    _stateHacker.emit(
                        StateHackerNews.GetDetailNew(
                            it.value,
                            it.value.id == mFavoriteNewsCache.id
                        )
                    )
                }
            }
        }
    }
}