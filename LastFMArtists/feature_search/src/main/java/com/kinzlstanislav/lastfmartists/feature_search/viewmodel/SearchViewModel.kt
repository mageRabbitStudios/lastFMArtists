package com.kinzlstanislav.lastfmartists.feature_search.viewmodel

import androidx.lifecycle.LiveData
import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.lastfmartists.architecture.core.model.Artist
import com.kinzlstanislav.lastfmartists.base.viewmodel.BaseViewModel

abstract class SearchViewModel(appCoroutineScope: AppCoroutineScope) : BaseViewModel(appCoroutineScope) {

    abstract val searchState: LiveData<FragmentSearchState>

    sealed class FragmentSearchState {

        data class ArtistsLoaded(val artists: List<Artist>) : FragmentSearchState()
        object LoadingArtists : FragmentSearchState()
        object FetchingArtistsNE : FragmentSearchState()
        object FetchingArtistsGE : FragmentSearchState()
    }

    abstract fun fetchLastfmArtistsSuggestions(artistName: String, limit: Int)

}