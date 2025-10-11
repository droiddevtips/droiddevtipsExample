package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailEvent
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailViewState
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ViewType
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.repository.ListDetailDemoRepository
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.repository.ListDetailDemoRepositoryImp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class ListDetailViewModel(
    private val viewType: ViewType,
    private val savedStateHandle: SavedStateHandle,
    private val repository: ListDetailDemoRepository = ListDetailDemoRepositoryImp()
): ViewModel() {

    private val _listDetailViewState = MutableStateFlow<ListDetailViewState>( getSavedState() ?: ListDetailViewState())
    val listDetailViewState: StateFlow<ListDetailViewState>
        get() = _listDetailViewState.asStateFlow().onStart {
            loadData()
        }.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000),ListDetailViewState())


    private suspend fun loadData() {
        val itemList = repository.loadData(viewType = viewType)

        if (itemList.isEmpty()) {
            _listDetailViewState.update { it.copy(showEmptyView = true, showLoadingView = false) }
            return
        }

        _listDetailViewState.update { it.copy(itemsList = itemList, showLoadingView = false) }
        saveState()
    }

    fun handleEvent(event: ListDetailEvent) {

        when (event) {

            is ListDetailEvent.NavigateToDetail -> {
                _listDetailViewState.update { it.copy(selectedItem = event.item) }
            }
            is ListDetailEvent.SetScrollPosition -> {
                _listDetailViewState.update { it.copy(visibleIndex = event.index) }
            }
            is ListDetailEvent.ToggleLoadingView -> {
                _listDetailViewState.update { it.copy(showLoadingView = event.visible) }
            }
        }

        saveState()
    }

    private fun saveState() {
        savedStateHandle[viewType.toString()] = listDetailViewState.value
    }

    private fun getSavedState(): ListDetailViewState? {
        return savedStateHandle.get<ListDetailViewState>(viewType.toString())
    }
}