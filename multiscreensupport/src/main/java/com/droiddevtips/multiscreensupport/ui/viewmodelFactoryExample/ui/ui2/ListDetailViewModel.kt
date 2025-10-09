package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.ui.ui2

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ListDetailViewState
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ViewType
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.repository.ListDetailDemoRepository
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.repository.ListDetailDemoRepositoryImp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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
        get() = _listDetailViewState.asStateFlow()

    private fun saveState() {
        savedStateHandle[viewType.toString()] = listDetailViewState.value
    }

    private fun getSavedState(): ListDetailViewState? {
        return savedStateHandle.get<ListDetailViewState>(viewType.toString())
    }
}