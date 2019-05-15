package com.kinzlstanislav.feature_search.viewmodel

import androidx.lifecycle.MutableLiveData
import com.kinzlstanislav.feature_search.viewmodel.FragmentSearchViewModel.FragmentSearchState.FetchingArtistsGE
import com.kinzlstanislav.feature_search.viewmodel.FragmentSearchViewModel.FragmentSearchState.FetchingArtistsNE
import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchLastfmArtistsByNameUseCase
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchLastfmArtistsByNameUseCase.Result

class FragmentSearchViewModelImpl(
    appCoroutineScope: AppCoroutineScope,
    override val searchState: MutableLiveData<FragmentSearchState> = MutableLiveData(),
    private val fetchLastfmArtistsByNameUseCase: FetchLastfmArtistsByNameUseCase
) : FragmentSearchViewModel(appCoroutineScope) {

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