package com.kinzlstanislav.lastfmartists.architecture.network.response

import com.squareup.moshi.Json

data class LastfmArtistSearchResultResponse(
    @field:Json(name = "results") val matchesResult: LastfmArtistSearchMatchesResponse? = null
)

data class LastfmArtistSearchMatchesResponse(
    @field:Json(name = "artistmatches") val artistsResult: LastfmArtistsResponse? = null
)

data class LastfmArtistsResponse(
    @field:Json(name = "artist") val match: List<LastfmArtistResponse>
)

data class LastfmArtistResponse(
    @field:Json(name = "name") val name: String? = null,
    @field:Json(name = "listeners") val listeners: String? = null,
    @field:Json(name = "mbid") val mbid: String? = null,
    @field:Json(name = "url") val lastfmArtistUrl: String? = null,
    @field:Json(name = "streamable") val streamable: String? = null,
    @field:Json(name = "image") val images: List<LastfmArtistImageResponse>? = null
)

data class LastfmArtistImageResponse(
    @field:Json(name = "#text") val url: String? = null,
    @field:Json(name = "size") val imageSize: String? = null
)