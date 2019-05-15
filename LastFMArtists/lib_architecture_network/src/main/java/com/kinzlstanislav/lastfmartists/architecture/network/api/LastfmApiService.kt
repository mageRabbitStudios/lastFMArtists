package com.kinzlstanislav.lastfmartists.architecture.network.api

import com.kinzlstanislav.lastfmartists.architecture.network.response.LastfmArtistSearchResultResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface LastfmApiService {

    @GET("2.0/")
    @Throws(Exception::class)
    fun getArtistsSearchResultAsync(
        @Query("method") searchType: String = "artist.search",
        @Query("artist") byName: String,
        @Query("limit") searchLimit: Int,
        @Query("api_key") apiKey: String = LastfmRestData.LASTFM_API_KEY,
        @Query("format") format: String = "json"
    ): Deferred<LastfmArtistSearchResultResponse>

}