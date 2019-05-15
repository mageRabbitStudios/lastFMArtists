package com.kinzlstanislav.lastfmartists.base.viewmodel

import androidx.lifecycle.ViewModel
import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AppCoroutineScope

abstract class BaseViewModel(appCoroutineScope: AppCoroutineScope) : ViewModel(), AppCoroutineScope by appCoroutineScope {

    override fun onCleared() {
        cancelAll()
        super.onCleared()
    }
}