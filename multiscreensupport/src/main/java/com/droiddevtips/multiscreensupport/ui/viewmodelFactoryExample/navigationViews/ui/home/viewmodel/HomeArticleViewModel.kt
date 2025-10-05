package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home.data.HomeArticleViewState
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home.data.HomeViewEvent
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home.data.repository.HomeArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class HomeArticleViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: HomeArticleRepository
) : ViewModel() {

    val _homeArticleViewState = MutableStateFlow( getSavedState() ?: HomeArticleViewState())
    val homeArticleViewState: StateFlow<HomeArticleViewState>
        get() = _homeArticleViewState.asStateFlow()

    private fun loadData() {
        Log.i("TAG34","Load home articles")
        viewModelScope.launch {
            val articles = repository.loadHomeArticles()
            _homeArticleViewState.update { it.copy(articles = articles, showLoadingView = false) }
            saveState()
        }
    }

    init {
        loadData()
    }

    fun handleEvent(event: HomeViewEvent) {

        when (event) {

            is HomeViewEvent.ToggleLoadingView -> {
                _homeArticleViewState.update { it.copy(showLoadingView = event.visible) }
            }

            is HomeViewEvent.NavigateToDetail -> {
                _homeArticleViewState.update { it.copy(selectedArticle = event.article) }
            }

            is HomeViewEvent.SetScrollPosition -> {
                _homeArticleViewState.update { it.copy(visibleIndex = event.index) }
            }
        }

        saveState()
    }

    private fun saveState() {
        savedStateHandle[this::class.java.simpleName] = homeArticleViewState.value
    }

    private fun getSavedState(): HomeArticleViewState? {

        val savedState = savedStateHandle.get<HomeArticleViewState>(this::class.java.simpleName)

        Log.i("TAG34","Saved state: $savedState")
        return savedState
    }
}