package com.kinzlstanislav.lastfmartists.architecture.network.mapper

import com.kinzlstanislav.lastfmartists.architecture.core.model.Artist
import com.kinzlstanislav.lastfmartists.architecture.network.mapper.exception.CriticalMappingException
import com.kinzlstanislav.lastfmartists.architecture.network.response.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

private val SOME_RESPONSE = LastfmArtistSearchResultResponse(
    matchesResult = LastfmArtistSearchMatchesResponse(
        artistsResult = LastfmArtistsResponse(
            matchesResult = listOf(
                LastfmArtistResponse(
                    name = "Stanislav",
                    listeners = "Who knows",
                    mbid = "1234",
                    lastfmArtistUrl = "url",
                    streamable = "Not likely",
                    images = listOf(
                        LastfmArtistImageResponse(
                            url = "url",
                            imageSize = "small!"
                        ),
                        LastfmArtistImageResponse(
                            url = "url",
                            imageSize = "big!"
                        )
                    )
                )
            )
        )
    )
)


private val SOME_RESPONSE_N1 = LastfmArtistSearchResultResponse(
    matchesResult = LastfmArtistSearchMatchesResponse(
        artistsResult = LastfmArtistsResponse(
            matchesResult = listOf(
                LastfmArtistResponse(
                    name = "Stanislav",
                    listeners = "Who knows",
                    mbid = "1234",
                    lastfmArtistUrl = "url",
                    streamable = "Not likely",
                    images = listOf(
                        LastfmArtistImageResponse(
                            url = "",
                            imageSize = ""
                        ),
                        LastfmArtistImageResponse(
                            url = null,
                            imageSize = null
                        )
                    )
                )
            )
        )
    )
)

private val SOME_RESPONSE_N2 = LastfmArtistSearchResultResponse(
    matchesResult = LastfmArtistSearchMatchesResponse(
        artistsResult = LastfmArtistsResponse(
            matchesResult = listOf(
                LastfmArtistResponse(
                    name = null,
                    listeners = null,
                    mbid = null,
                    lastfmArtistUrl = null,
                    streamable = null,
                    images = null
                )
            )
        )
    )
)

private val SOME_RESPONSE_E1 = LastfmArtistSearchResultResponse(
    matchesResult = LastfmArtistSearchMatchesResponse(
        artistsResult = LastfmArtistsResponse(
            matchesResult = null
        )
    )
)

private val SOME_RESPONSE_E2 = LastfmArtistSearchResultResponse(
    matchesResult = LastfmArtistSearchMatchesResponse(
        artistsResult = null
    )
)

private val SOME_RESPONSE_E3 = LastfmArtistSearchResultResponse(
    matchesResult = null
)


private val EXPECTED_RESULT = listOf(Artist("Stanislav", "1234", "url"))
private val EXPECTED_RESULT_N1 = listOf(Artist("Stanislav", "1234", ""))
private val EXPECTED_RESULT_N2 = listOf(Artist("", "", ""))

class LastfmArtistResponseMapperTest {

    var subject = LastfmArtistResponseMapper()

    @Test
    fun `mapFromArtistsResponse() - maps list`() {
        val result = subject.mapFromArtistsResponse(SOME_RESPONSE)
        assertThat(result).isEqualTo(EXPECTED_RESULT)
    }

    @Test
    fun `mapFromArtistsResponse() - maps with null values`() {
        assertThat(subject.mapFromArtistsResponse(SOME_RESPONSE_N1))
            .isEqualTo(EXPECTED_RESULT_N1)
        assertThat(subject.mapFromArtistsResponse(SOME_RESPONSE_N2))
            .isEqualTo(EXPECTED_RESULT_N2)
    }

    @Test(expected = CriticalMappingException::class)
    fun `mapFromArtistsResponse() - throws CriticalMappingException 1`() {
        subject.mapFromArtistsResponse(SOME_RESPONSE_E1)
    }

    @Test(expected = CriticalMappingException::class)
    fun `mapFromArtistsResponse() - throws CriticalMappingException 2`() {
        subject.mapFromArtistsResponse(SOME_RESPONSE_E2)
    }

    @Test(expected = CriticalMappingException::class)
    fun `mapFromArtistsResponse() - throws CriticalMappingException 3`() {
        subject.mapFromArtistsResponse(SOME_RESPONSE_E3)
    }

}