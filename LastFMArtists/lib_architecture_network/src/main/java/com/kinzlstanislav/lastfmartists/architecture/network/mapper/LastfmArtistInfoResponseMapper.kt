package com.kinzlstanislav.lastfmartists.architecture.network.mapper

import com.kinzlstanislav.lastfmartists.architecture.core.model.ArtistInfo
import com.kinzlstanislav.lastfmartists.architecture.network.response.LastfmArtistInfoResponse
import javax.inject.Inject

class LastfmArtistInfoResponseMapper @Inject constructor() {

    fun mapFromArtistInfoResponse(response: LastfmArtistInfoResponse): ArtistInfo {
        return ArtistInfo("summary")
    }
}