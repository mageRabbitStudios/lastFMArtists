package com.kinzlstanislav.lastfmartists.feature_artist_detail.viewmodel

import androidx.lifecycle.LiveData
import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.lastfmartists.architecture.core.model.ArtistInfo
import com.kinzlstanislav.lastfmartists.base.viewmodel.BaseViewModel

abstract class ArtistDetailViewModel(appCoroutineScope: AppCoroutineScope) : BaseViewModel(appCoroutineScope) {

    abstract val artistInfoState: LiveData<ArtistDetailInfoState>

    sealed class ArtistDetailInfoState {

        data class ArtistInfoLoaded(val artistInfo: ArtistInfo) : ArtistDetailInfoState()
        object LoadingArtistInfo : ArtistDetailInfoState()
        object FetchingArtistInfoNE : ArtistDetailInfoState()
        object FetchingArtistInfoGE : ArtistDetailInfoState()
    }

    abstract fun fetchLastfmArtistById(mbid: String)

}