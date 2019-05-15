package com.kinzlstanislav.lastfmartists.feature_search.view

import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.kinzlstanislav.lastfmartists.feature_search.R
import com.kinzlstanislav.lastfmartists.feature_search.view.adapter.ArtistItemClickListener
import com.kinzlstanislav.lastfmartists.feature_search.view.adapter.ArtistsSearchListAdapter
import com.kinzlstanislav.lastfmartists.feature_search.viewmodel.SearchViewModel
import com.kinzlstanislav.lastfmartists.architecture.core.model.Artist
import com.kinzlstanislav.lastfmartists.base.ArgumentConstants.EXTRAS_ARTIST
import com.kinzlstanislav.lastfmartists.base.extension.*
import com.kinzlstanislav.lastfmartists.base.imageloading.GlideImageLoader
import com.kinzlstanislav.lastfmartists.base.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_search.artists_list_recycler_view as list
import kotlinx.android.synthetic.main.fragment_search.contributors_list_loader as loader

class FragmentSearch : BaseFragment(), ArtistItemClickListener {

    private companion object {
        const val ARTIST_LOAD_LIMIT = 20
    }

    @Inject
    lateinit var viewModel: SearchViewModel

    @Inject
    lateinit var imageLoader: GlideImageLoader

    override val layoutResourceId: Int = R.layout.fragment_search

    private val artistsAdapter: ArtistsSearchListAdapter by lazy { ArtistsSearchListAdapter(imageLoader, this) }

    override fun onFragmentCreated() {
        list.adapter = artistsAdapter
        observe(viewModel.searchState, ::handleState)
        setUpSearching()
    }

    private fun handleState(state: SearchViewModel.FragmentSearchState) = when (state) {
        is SearchViewModel.FragmentSearchState.ArtistsLoaded -> {
            artistsAdapter.updateItems(state.artists)
            loader.hide()
            list.show()
        }
        is SearchViewModel.FragmentSearchState.LoadingArtists -> {
            list.hide()
            loader.show()
        }
        is SearchViewModel.FragmentSearchState.FetchingArtistsNE -> {
            showToast("Fetching Artists NE")
        }
        is SearchViewModel.FragmentSearchState.FetchingArtistsGE -> {
            showToast("Fetching Artists GE")
        }
    }

    private fun setUpSearching() {
        artists_search.doOnSearch {
            if (query.isNotEmpty()) {
                viewModel.fetchLastfmArtistsSuggestions(query.toString(), ARTIST_LOAD_LIMIT)
            }
        }
    }

    override fun onArtistItemClicked(artist: Artist) {
        findNavController().navigate(R.id.search_to_artist_detail, bundleOf(EXTRAS_ARTIST to artist))
    }

}