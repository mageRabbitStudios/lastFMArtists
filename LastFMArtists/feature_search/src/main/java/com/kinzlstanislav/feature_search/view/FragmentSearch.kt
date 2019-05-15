package com.kinzlstanislav.feature_search.view

import android.view.View
import androidx.appcompat.widget.SearchView
import com.kinzlstanislav.feature_search.R
import com.kinzlstanislav.feature_search.view.adapter.ArtistsSearchListAdapter
import com.kinzlstanislav.feature_search.view.adapter.ArtistItemClickListener
import com.kinzlstanislav.feature_search.viewmodel.FragmentSearchViewModel
import com.kinzlstanislav.lastfmartists.architecture.core.model.Artist
import com.kinzlstanislav.lastfmartists.base.extension.*
import com.kinzlstanislav.lastfmartists.base.imageloading.GlideImageLoader
import com.kinzlstanislav.lastfmartists.base.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.contributors_list_loader as loader
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_search.artists_list_recycler_view as list

class FragmentSearch : BaseFragment(), ArtistItemClickListener {

    private companion object {
        const val ARTIST_LOAD_LIMIT = 20
    }

    @Inject
    lateinit var viewModel: FragmentSearchViewModel

    @Inject
    lateinit var imageLoader: GlideImageLoader

    override val layoutResourceId: Int = R.layout.fragment_search

    private val artistsAdapter: ArtistsSearchListAdapter by lazy { ArtistsSearchListAdapter(imageLoader, this) }

    override fun onFragmentCreated() {
        list.adapter = artistsAdapter
        observe(viewModel.searchState, ::handleState)
        setUpSearching()
    }

    private fun handleState(state: FragmentSearchViewModel.FragmentSearchState) = when (state) {
        is FragmentSearchViewModel.FragmentSearchState.ArtistsLoaded -> {
            artistsAdapter.updateItems(state.artists)
            loader.hide()
            list.show()
        }
        is FragmentSearchViewModel.FragmentSearchState.LoadingArtists -> {
            list.hide()
            loader.show()
        }
        is FragmentSearchViewModel.FragmentSearchState.FetchingArtistsNE -> {
            showToast("Fetching Artists NE")
        }
        is FragmentSearchViewModel.FragmentSearchState.FetchingArtistsGE -> {
            showToast("Fetching Artists GE")
        }
    }
    
    private fun setUpSearching() {
        artists_search.doOnSearch {
            if (!query.toString().isEmpty()) {
                viewModel.fetchLastfmArtistsSuggestions(query.toString(), ARTIST_LOAD_LIMIT)
            }
        }
    }

    override fun onArtistItemClicked(artist: Artist) {
        showToast(artist.name)
    }

}