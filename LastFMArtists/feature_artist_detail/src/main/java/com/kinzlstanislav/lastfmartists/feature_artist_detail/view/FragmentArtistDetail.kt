package com.kinzlstanislav.lastfmartists.feature_artist_detail.view

import com.kinzlstanislav.lastfmartists.architecture.core.model.Artist
import com.kinzlstanislav.lastfmartists.base.ArgumentConstants.EXTRAS_ARTIST
import com.kinzlstanislav.lastfmartists.base.extension.bindArgument
import com.kinzlstanislav.lastfmartists.base.extension.showToast
import com.kinzlstanislav.lastfmartists.base.view.BaseFragment
import com.kinzlstanislav.lastfmartists.feature_artist_detail.R

class FragmentArtistDetail : BaseFragment() {

    private val artist: Artist by bindArgument(EXTRAS_ARTIST)

    override val layoutResourceId = R.layout.fragment_artist_detail

    override fun onFragmentCreated() {
        showToast(artist.name)
    }

}