package com.kinzlstanislav.lastfmartists.feature_search.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kinzlstanislav.lastfmartists.feature_search.viewmodel.SearchViewModel.FragmentSearchState.FetchingArtistsGE
import com.kinzlstanislav.lastfmartists.feature_search.viewmodel.SearchViewModel.FragmentSearchState.FetchingArtistsNE
import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchLastfmArtistsByNameUseCase
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchLastfmArtistsByNameUseCase.Result

class SearchViewModelImpl(
    appCoroutineScope: AppCoroutineScope,
    override val searchState: MutableLiveData<FragmentSearchState> = MutableLiveData(),
    private val fetchLastfmArtistsByNameUseCase: FetchLastfmArtistsByNameUseCase
) : SearchViewModel(appCoroutineScope) {

    override fun fetchLastfmArtistsSuggestions(artistName: String, limit: Int) {
        searchState.value = FragmentSearchState.LoadingArtists
        uiJob {
            val result = fetchLastfmArtistsByNameUseCase.execute(artistName, limit)
            when (result) {
                is Result.GenericError -> searchState.value = FetchingArtistsGE
                is Result.NetworkError -> searchState.value = FetchingArtistsNE
                is Result.Success -> searchState.value = FragmentSearchState.ArtistsLoaded(result.artists)
            }
        }
    }

}