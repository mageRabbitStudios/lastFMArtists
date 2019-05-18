package com.kinzlstanislav.lastfmartists.architecture.network.mapper

import com.kinzlstanislav.lastfmartists.architecture.core.model.Artist
import com.kinzlstanislav.lastfmartists.architecture.network.mapper.exception.CriticalMappingException
import com.kinzlstanislav.lastfmartists.architecture.network.response.LastfmArtistResponse
import com.kinzlstanislav.lastfmartists.architecture.network.response.LastfmArtistSearchResultResponse
import java.util.*
import javax.inject.Inject

class LastfmArtistResponseMapper @Inject constructor() {

    fun mapFromArtistsResponse(response: LastfmArtistSearchResultResponse): List<Artist> {
        val artistSearchResponse = response.matchesResult ?: throw CriticalMappingException("results null")
        val artistSearchMatchesResponse =
            artistSearchResponse.artistsResult ?: throw CriticalMappingException("artistmatches null")
        val artistsResponse =
            artistSearchMatchesResponse.matchesResult ?: throw CriticalMappingException("artists null")

        val list = mutableListOf<Artist>()
        artistsResponse.forEach {
            list.add(mapFromArtistResponse(it))
        }
        return list
    }

    private fun mapFromArtistResponse(response: LastfmArtistResponse) =
        Artist(
            name = response.name ?: "",
            id = response.mbid ?: "",
            imageUrl = response.images?.asReversed()?.first()?.url?: ""
        )
}