package com.kinzlstanislav.lastfmartists.feature_search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.kinzlstanislav.lastfmartists.architecture.core.model.Artist
import com.kinzlstanislav.lastfmartists.base.ArgumentConstants.EXTRAS_ARTIST
import com.kinzlstanislav.lastfmartists.base.extension.*
import com.kinzlstanislav.lastfmartists.base.imageloading.GlideImageLoader
import com.kinzlstanislav.lastfmartists.base.view.BaseFragment
import com.kinzlstanislav.lastfmartists.feature_search.R
import com.kinzlstanislav.lastfmartists.feature_search.view.adapter.ArtistItemClickListener
import com.kinzlstanislav.lastfmartists.feature_search.view.adapter.ArtistsSearchListAdapter
import com.kinzlstanislav.lastfmartists.feature_search.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject
import com.kinzlstanislav.lastfmartists.feature_search.R.id.generic_error_refresh_button as genericErrButton
import com.kinzlstanislav.lastfmartists.feature_search.R.id.network_error_refresh_button as networkErrButton
import kotlinx.android.synthetic.main.fragment_search.artists_list_recycler_view as list
import kotlinx.android.synthetic.main.fragment_search.flipper_search as flipper

class FragmentSearch : BaseFragment(), ArtistItemClickListener {

    private companion object {
        const val ARTIST_LOAD_LIMIT = 20
    }

    @Inject
    lateinit var viewModel: SearchViewModel

    @Inject
    lateinit var imageLoader: GlideImageLoader

    override val layoutResourceId: Int = R.layout.fragment_search

    private var savedView: View? = null

    private val artistsAdapter: ArtistsSearchListAdapter by lazy { ArtistsSearchListAdapter(imageLoader, this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = savedView ?: super.onCreateView(inflater, container, savedInstanceState)


    override fun onFragmentCreated() {
        observe(viewModel.searchState, ::handleState)
        if (savedView == null) {
            flipper.showView(search_screen_welcome)
            list.adapter = artistsAdapter
            setUpSearching()
            setErrorButtonsActions()
        }
    }

    private fun handleState(state: SearchViewModel.FragmentSearchState) = when (state) {
        is SearchViewModel.FragmentSearchState.ArtistsLoaded -> {
            flipper.hide()
            artistsAdapter.updateItems(state.artists)
            list.show()
        }
        is SearchViewModel.FragmentSearchState.LoadingArtists ->
            list.hide().run { flipper.showView(contributors_list_loader) }
        is SearchViewModel.FragmentSearchState.FetchingArtistsNE ->
            list.hide().run { flipper.showView(search_network_error) }
        is SearchViewModel.FragmentSearchState.FetchingArtistsGE ->
            list.hide().run { flipper.showView(search_generic_error) }
    }

    private fun setUpSearching() {
        artists_search.doOnSearch {
            if (query.isNotEmpty()) {
                fetchArtistsFromSearchQuery()
            }
        }
    }

    override fun onDestroyView() {
        savedView = view
        super.onDestroyView()
    }

    private fun setErrorButtonsActions() {
        onClickOn(genericErrButton) { fetchArtistsFromSearchQuery() }
        onClickOn(networkErrButton) { fetchArtistsFromSearchQuery() }
    }

    override fun onArtistItemClicked(artist: Artist) {
        findNavController().navigate(R.id.search_to_artist_detail, bundleOf(EXTRAS_ARTIST to artist))
    }

    private fun fetchArtistsFromSearchQuery() {
        if (artists_search.query.isNotEmpty()) {
            viewModel.fetchLastfmArtistsSuggestions(artists_search.query.toString(), ARTIST_LOAD_LIMIT)
        }
    }

}