package com.kinzlstanislav.lastfmartists.architecture.network.mapper

import com.kinzlstanislav.lastfmartists.architecture.core.model.Artist
import com.kinzlstanislav.lastfmartists.architecture.network.response.LastfmArtistSearchMatchesResponse
import com.kinzlstanislav.lastfmartists.architecture.network.response.LastfmArtistSearchResultResponse
import javax.inject.Inject

class LastfmArtistResponseMapper @Inject constructor() {

    fun mapFromArtistsResponse(response: LastfmArtistSearchResultResponse): List<Artist> {
        return emptyList()
    }

}