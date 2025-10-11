package com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ViewType
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.repository.ListDetailDemoRepository

/**
 * The list detail view model factory, a subclass of [ViewModelProvider.Factory]
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class ListDetailViewModelFactory(
    private val repository: ListDetailDemoRepository,
    private val viewType: ViewType
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(ListDetailViewModel::class.java)) {
            return ListDetailViewModel(
                viewType = viewType,
                savedStateHandle = extras.createSavedStateHandle(),
                repository = repository
            ) as T
        }

        throw IllegalArgumentException("Not ${ListDetailViewModel::class.simpleName} class")
    }
}