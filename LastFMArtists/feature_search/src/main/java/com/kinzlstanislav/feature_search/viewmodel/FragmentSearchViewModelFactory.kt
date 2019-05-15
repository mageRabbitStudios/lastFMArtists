package com.kinzlstanislav.feature_search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchLastfmArtistsByNameUseCase

class FragmentSearchViewModelFactory(
    private val appCoroutineScope: AppCoroutineScope,
    private val fetchLastfmArtistsByNameUseCase: FetchLastfmArtistsByNameUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = FragmentSearchViewModelImpl(
        appCoroutineScope = appCoroutineScope,
        fetchLastfmArtistsByNameUseCase = fetchLastfmArtistsByNameUseCase
    ) as T

}