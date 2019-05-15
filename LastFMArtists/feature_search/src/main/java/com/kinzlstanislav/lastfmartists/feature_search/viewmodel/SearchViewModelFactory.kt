package com.kinzlstanislav.lastfmartists.feature_search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchLastfmArtistsByNameUseCase

class SearchViewModelFactory(
    private val appCoroutineScope: AppCoroutineScope,
    private val fetchLastfmArtistsByNameUseCase: FetchLastfmArtistsByNameUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = SearchViewModelImpl(
        appCoroutineScope = appCoroutineScope,
        fetchLastfmArtistsByNameUseCase = fetchLastfmArtistsByNameUseCase
    ) as T

}