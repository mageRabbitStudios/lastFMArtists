package com.kinzlstanislav.lastfmartists.architecture.network.mapper

import com.kinzlstanislav.lastfmartists.architecture.core.model.ArtistInfo
import com.kinzlstanislav.lastfmartists.architecture.core.model.ArtistInfoTag
import com.kinzlstanislav.lastfmartists.architecture.network.response.LastfmArtistInfoResponse
import com.kinzlstanislav.lastfmartists.architecture.network.response.LastfmArtistTagsResponse
import javax.inject.Inject

class LastfmArtistInfoResponseMapper @Inject constructor() {

    fun mapFromArtistInfoResponse(response: LastfmArtistInfoResponse) =
            ArtistInfo(
                tags = response.artistInfo?.tags?.let { mapFromArtistTagsResponse(it) } ?: emptyList(),
                published = response.artistInfo?.bio?.published?: "",
                summary = response.artistInfo?.bio?.summary?: "",
                content = response.artistInfo?.bio?.content?: ""
            )

    private fun mapFromArtistTagsResponse(response: LastfmArtistTagsResponse): List<ArtistInfoTag> {
        val list = mutableListOf<ArtistInfoTag>()
        response.list?.forEach {
            it.name?.let { tag -> list.add(ArtistInfoTag(tag)) }
        }
        return list
    }

}