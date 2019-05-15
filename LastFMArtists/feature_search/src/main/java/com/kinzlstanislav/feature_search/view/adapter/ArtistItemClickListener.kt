package com.kinzlstanislav.feature_search.view.adapter

import com.kinzlstanislav.lastfmartists.architecture.core.model.Artist

interface ArtistItemClickListener {

    fun onArtistItemClicked(artist: Artist)

}