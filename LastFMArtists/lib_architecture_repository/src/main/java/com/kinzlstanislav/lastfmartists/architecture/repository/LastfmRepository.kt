package com.kinzlstanislav.lastfmartists.architecture.repository

import com.kinzlstanislav.lastfmartists.architecture.core.model.Artist
import com.kinzlstanislav.lastfmartists.architecture.network.api.LastfmApiService
import com.kinzlstanislav.lastfmartists.architecture.network.mapper.LastfmArtistResponseMapper
import java.io.IOException
import javax.inject.Inject

class LastfmRepository @Inject constructor(
    private val api: LastfmApiService,
    private val mapper: LastfmArtistResponseMapper
) {

    @Throws(IOException::class)
    suspend fun getArtistsSearchResult(byName: String, limit: Int): List<Artist> {
        val response = api.getArtistsSearchResultAsync(
            byName = byName,
            searchLimit = limit).await()
        return mapper.mapFromArtistsResponse(response)
    }

}