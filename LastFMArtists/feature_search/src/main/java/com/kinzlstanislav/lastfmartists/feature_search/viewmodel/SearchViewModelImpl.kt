package com.kinzlstanislav.lastfmartists.feature_search.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchLastfmArtistsByNameUseCase
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchLastfmArtistsByNameUseCase.Result
import com.kinzlstanislav.lastfmartists.feature_search.viewmodel.SearchViewModel.FragmentSearchState.*

class SearchViewModelImpl(
    appCoroutineScope: AppCoroutineScope,
    override val searchState: MutableLiveData<FragmentSearchState> = MutableLiveData(),
    private val fetchLastfmArtistsByNameUseCase: FetchLastfmArtistsByNameUseCase
) : SearchViewModel(appCoroutineScope) {

    override fun fetchLastfmArtistsSuggestions(artistName: String, limit: Int) {
        searchState.value = FragmentSearchState.LoadingArtists
        cancelAllJobs()
        uiJob {
            val result = fetchLastfmArtistsByNameUseCase.execute(artistName, limit)
            when (result) {
                is Result.GenericError -> searchState.value = FetchingArtistsGE
                is Result.NetworkError -> searchState.value = FetchingArtistsNE
                is Result.Success -> searchState.value = ArtistsLoaded(result.artists)
            }
        }
    }

}