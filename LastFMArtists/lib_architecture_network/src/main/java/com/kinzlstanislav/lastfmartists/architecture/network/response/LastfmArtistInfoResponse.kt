package com.kinzlstanislav.lastfmartists.architecture.network.response

import com.squareup.moshi.Json

data class LastfmArtistInfoResponse(
    @field:Json(name = "artist") val artistInfo: LastFmArtistInfoResponseNested? = null
)

data class LastFmArtistInfoResponseNested(
    @field:Json(name = "bio") val bio: LastfmArtistBioResponse? = null,
    @field:Json(name = "tags") val tags: LastfmArtistTagsResponse? = null
)
data class LastfmArtistTagsResponse(
    @field:Json(name = "tag") val list: List<LastfmArtistTagResponse>? = null
)

data class LastfmArtistTagResponse(
    @field:Json(name = "name") val name: String? = null,
    @field:Json(name = "url") val url: String? = null
)

data class LastfmArtistBioResponse(
    @field:Json(name = "published") val published: String? = null,
    @field:Json(name = "summary") val summary: String? = null,
    @field:Json(name = "content") val content: String? = null
)