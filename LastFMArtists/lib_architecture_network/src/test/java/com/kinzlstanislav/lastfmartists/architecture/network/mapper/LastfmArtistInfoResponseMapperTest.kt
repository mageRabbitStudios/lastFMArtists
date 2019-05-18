package com.kinzlstanislav.lastfmartists.architecture.network.mapper

import com.kinzlstanislav.lastfmartists.architecture.core.model.ArtistInfo
import com.kinzlstanislav.lastfmartists.architecture.network.response.LastFmArtistInfoResponseNested
import com.kinzlstanislav.lastfmartists.architecture.network.response.LastfmArtistBioResponse
import com.kinzlstanislav.lastfmartists.architecture.network.response.LastfmArtistInfoResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

private val SOME_RESPONSE = LastfmArtistInfoResponse(
    artistInfo = LastFmArtistInfoResponseNested(
        bio = LastfmArtistBioResponse(
            published = "12.01.20189",
            summary = "summary",
            content = "content"
        )
    )
)

private val SOME_RESPONSE_WITH_NULL_VALUES1 = LastfmArtistInfoResponse(
    artistInfo = LastFmArtistInfoResponseNested(
        bio = LastfmArtistBioResponse(
            published = null,
            summary = null,
            content = null
        )
    )
)

private val SOME_RESPONSE_WITH_NULL_VALUES2 = LastfmArtistInfoResponse(
    artistInfo = LastFmArtistInfoResponseNested(
        bio = null
    )
)

private val SOME_RESPONSE_WITH_NULL_VALUES3 = LastfmArtistInfoResponse(
    artistInfo = null
)

private val EXPECTED_NULL_VALUES_RESULT = ArtistInfo("", "", "")

class LastfmArtistInfoResponseMapperTest {

    var subject = LastfmArtistInfoResponseMapper()

    @Test
    fun `mapFromArtistInfoResponse() - maps`() {
        val result = subject.mapFromArtistInfoResponse(SOME_RESPONSE)
        assertThat(result.content).isEqualTo("content")
        assertThat(result.summary).isEqualTo("summary")
        assertThat(result.published).isEqualTo("12.01.20189")
    }

    @Test
    fun `mapFromArtistInfoResponse() - maps with null input values`() {
        assertThat(subject.mapFromArtistInfoResponse(SOME_RESPONSE_WITH_NULL_VALUES1))
            .isEqualTo(EXPECTED_NULL_VALUES_RESULT)
        assertThat(subject.mapFromArtistInfoResponse(SOME_RESPONSE_WITH_NULL_VALUES2))
            .isEqualTo(EXPECTED_NULL_VALUES_RESULT)
        assertThat(subject.mapFromArtistInfoResponse(SOME_RESPONSE_WITH_NULL_VALUES3))
            .isEqualTo(EXPECTED_NULL_VALUES_RESULT)
    }

}