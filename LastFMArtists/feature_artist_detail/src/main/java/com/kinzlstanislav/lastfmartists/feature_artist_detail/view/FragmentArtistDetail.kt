package com.kinzlstanislav.lastfmartists.feature_artist_detail.view

import android.annotation.SuppressLint
import com.kinzlstanislav.lastfmartists.architecture.core.model.Artist
import com.kinzlstanislav.lastfmartists.architecture.core.model.ArtistInfo
import com.kinzlstanislav.lastfmartists.base.ArgumentConstants.EXTRAS_ARTIST
import com.kinzlstanislav.lastfmartists.base.extension.bindArgument
import com.kinzlstanislav.lastfmartists.base.extension.observe
import com.kinzlstanislav.lastfmartists.base.extension.showToast
import com.kinzlstanislav.lastfmartists.base.view.BaseFragment
import com.kinzlstanislav.lastfmartists.feature_artist_detail.R
import com.kinzlstanislav.lastfmartists.feature_artist_detail.viewmodel.ArtistDetailViewModel
import com.kinzlstanislav.lastfmartists.feature_artist_detail.viewmodel.ArtistDetailViewModel.ArtistDetailInfoState.*
import kotlinx.android.synthetic.main.fragment_artist_detail.*
import javax.inject.Inject

class FragmentArtistDetail : BaseFragment() {

    @Inject
    lateinit var viewModel: ArtistDetailViewModel

    private val artist: Artist by bindArgument(EXTRAS_ARTIST)

    override val layoutResourceId = R.layout.fragment_artist_detail

    override fun onFragmentCreated() {
        observe(viewModel.artistInfoState, ::handleState)
        viewModel.fetchLastfmArtistById(artist.id)
    }

    private fun handleState(state: ArtistDetailViewModel.ArtistDetailInfoState) = when (state) {
        is ArtistInfoLoaded -> updateTv(state.artistInfo)
        is LoadingArtistInfo -> showToast("Loading Artist Info")
        is FetchingArtistInfoNE -> showToast("Fetching Artist Info Network Error")
        is FetchingArtistInfoGE -> showToast("Fetching Artist Info Generic Error")
    }

    @SuppressLint("SetTextI18n")
    private fun updateTv(artistInfo: ArtistInfo) {
        shit.text = "${artist.name}\n\n" +
                "${artistInfo.published}\n\n" +
                "${artistInfo.summary}\n\n" +
                artistInfo.content
    }

}