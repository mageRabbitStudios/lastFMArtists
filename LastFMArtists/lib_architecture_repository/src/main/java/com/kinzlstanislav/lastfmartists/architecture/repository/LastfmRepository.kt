package com.kinzlstanislav.lastfmartists.architecture.repository

import com.kinzlstanislav.lastfmartists.architecture.core.model.Artist
import com.kinzlstanislav.lastfmartists.architecture.core.model.ArtistInfo
import com.kinzlstanislav.lastfmartists.architecture.network.api.LastfmApiService
import com.kinzlstanislav.lastfmartists.architecture.network.mapper.LastfmArtistInfoResponseMapper
import com.kinzlstanislav.lastfmartists.architecture.network.mapper.LastfmArtistResponseMapper
import java.io.IOException
import javax.inject.Inject

class LastfmRepository @Inject constructor(
    private val api: LastfmApiService,
    private val lastfmArtistResponseMapper: LastfmArtistResponseMapper,
    private val lastfmArtistInfoResponseMapper: LastfmArtistInfoResponseMapper
) {

    @Throws(IOException::class)
    suspend fun getArtistsSearchResult(byName: String, limit: Int): List<Artist> {
        val response = api.getArtistsSearchResultAsync(
            byName = byName,
            searchLimit = limit).await()
        return lastfmArtistResponseMapper.mapFromArtistsResponse(response)
    }

    @Throws(IOException::class)
    suspend fun getArtistInfo(mbid: String): ArtistInfo {
        val response = api.getArtistInfoResultAsync(mbid = mbid).await()
        return lastfmArtistInfoResponseMapper.mapFromArtistInfoResponse(response)
    }

}