package com.kinzlstanislav.lastfmartists.feature_artist_detail.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchArtistInfoUseCase
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchArtistInfoUseCase.Result.*
import com.kinzlstanislav.lastfmartists.feature_artist_detail.viewmodel.ArtistDetailViewModel.ArtistDetailInfoState.*

class ArtistDetailViewModelImpl(
    appCoroutineScope: AppCoroutineScope,
    override val artistInfoState: MutableLiveData<ArtistDetailInfoState> = MutableLiveData(),
    private val fetchArtistInfoUseCase: FetchArtistInfoUseCase
) : ArtistDetailViewModel(appCoroutineScope) {

    override fun fetchLastfmArtistByMbid(mbid: String) {
        artistInfoState.value = ArtistDetailInfoState.LoadingArtistInfo
        uiJob {
            val result = fetchArtistInfoUseCase.executeWithMbid(mbid)
            when (result) {
                is GenericError -> artistInfoState.value = FetchingArtistInfoGE
                is NetworkError -> artistInfoState.value = FetchingArtistInfoNE
                is Success -> artistInfoState.value = ArtistInfoLoaded(result.artistInfo)
            }
        }
    }

}