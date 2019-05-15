package com.kinzlstanislav.feature_search.view

import com.kinzlstanislav.feature_search.R
import com.kinzlstanislav.feature_search.viewmodel.FragmentSearchViewModel
import com.kinzlstanislav.lastfmartists.base.extension.observe
import com.kinzlstanislav.lastfmartists.base.extension.showToast
import com.kinzlstanislav.lastfmartists.base.view.BaseFragment
import javax.inject.Inject

class FragmentSearch : BaseFragment() {

    @Inject
    lateinit var viewModel: FragmentSearchViewModel

    override val layoutResourceId: Int = R.layout.fragment_search

    override fun onFragmentCreated() {
        observe(viewModel.searchState, ::handleState)
        viewModel.fetchLastfmArtistsSuggestions("A", 2)
    }

    private fun handleState(state: FragmentSearchViewModel.FragmentSearchState) = when(state) {
        is FragmentSearchViewModel.FragmentSearchState.ArtistsLoaded -> {
            showToast("Artists Loaded " + state.artists)
        }
        is FragmentSearchViewModel.FragmentSearchState.LoadingArtists -> {
            showToast("Loading Artists")
        }
        is FragmentSearchViewModel.FragmentSearchState.FetchingArtistsNE -> {
            showToast("Fetching Artists NE")
        }
        is FragmentSearchViewModel.FragmentSearchState.FetchingArtistsGE -> {
            showToast("Fetching Artists GE")
        }
    }

}