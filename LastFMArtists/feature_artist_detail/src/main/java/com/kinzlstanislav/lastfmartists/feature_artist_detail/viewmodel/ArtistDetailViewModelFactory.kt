package com.kinzlstanislav.lastfmartists.feature_artist_detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchArtistInfoUseCase

@Suppress("UNCHECKED_CAST")
class ArtistDetailViewModelFactory(
    private val appCoroutineScope: AppCoroutineScope,
    private val fetchArtistInfoUseCase: FetchArtistInfoUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ArtistDetailViewModelImpl(
            appCoroutineScope = appCoroutineScope,
            fetchArtistInfoUseCase = fetchArtistInfoUseCase
        ) as T
}