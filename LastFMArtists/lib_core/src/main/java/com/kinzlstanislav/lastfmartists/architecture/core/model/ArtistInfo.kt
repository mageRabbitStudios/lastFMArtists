package com.kinzlstanislav.lastfmartists.architecture.core.model

data class ArtistInfo(
    val tags: List<ArtistInfoTag>,
    val published: String,
    val summary: String,
    val content: String
)

data class ArtistInfoTag(
    val name: String
)