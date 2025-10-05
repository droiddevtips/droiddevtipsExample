@file:Suppress("UNCHECKED_CAST")

package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.navigationViews.ui.home.data.repository.HomeArticleRepository

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class HomeViewModelFactory(private val repository: HomeArticleRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(HomeArticleViewModel::class.java)) {
            return HomeArticleViewModel(
                savedStateHandle = extras.createSavedStateHandle(),
                repository = repository
            ) as T
        }

        throw IllegalArgumentException("Not ${HomeArticleViewModel::class.simpleName} class")
    }
}