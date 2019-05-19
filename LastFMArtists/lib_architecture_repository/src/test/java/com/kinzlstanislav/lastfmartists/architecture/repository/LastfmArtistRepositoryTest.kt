package com.kinzlstanislav.lastfmartists.architecture.repository

import com.kinzlstanislav.lastfmartists.architecture.core.model.Artist
import com.kinzlstanislav.lastfmartists.architecture.core.model.ArtistInfo
import com.kinzlstanislav.lastfmartists.architecture.network.api.LastfmApiService
import com.kinzlstanislav.lastfmartists.architecture.network.mapper.LastfmArtistInfoResponseMapper
import com.kinzlstanislav.lastfmartists.architecture.network.mapper.LastfmArtistResponseMapper
import com.kinzlstanislav.lastfmartists.architecture.network.response.LastfmArtistInfoResponse
import com.kinzlstanislav.lastfmartists.architecture.network.response.LastfmArtistSearchResultResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.lang.Exception

private val SOME_ARTIST_LIST = listOf(Artist("User", "123", "url"))
private val SOME_ARTIST_INFO = ArtistInfo("someday", "summary", "content")

class LastfmArtistRepositoryTest {

    var api = mockk<LastfmApiService>()

    var deferredSearchResult = mockk<Deferred<LastfmArtistSearchResultResponse>>()
    var artistsResponse = mockk<LastfmArtistSearchResultResponse>()
    var artistsResponseMapper = mockk<LastfmArtistResponseMapper>()


    var deferredArtistInfoResult = mockk<Deferred<LastfmArtistInfoResponse>>()
    var artistInfoResponse = mockk<LastfmArtistInfoResponse>()
    var artistInfoResponseMapper = mockk<LastfmArtistInfoResponseMapper>()

    var subject = LastfmArtistRepository(api, artistsResponseMapper, artistInfoResponseMapper)

    @Test
    fun `getArtistsSearchResult() - returns list of artists`() {
        every { api.getArtistsSearchResultAsync(any(), any(), any(), any()) } returns deferredSearchResult
        coEvery { deferredSearchResult.await() } returns artistsResponse
        every { artistsResponseMapper.mapFromArtistsResponse(artistsResponse) } returns SOME_ARTIST_LIST

        val actualList = runBlocking { subject.getArtistsSearchResult("", 0) }

        assertThat(actualList).isEqualTo(SOME_ARTIST_LIST)
    }

    @Test(expected = Exception::class)
    fun `getArtistsSearchResult() - throws Exception if api is not successful`() {
        every { api.getArtistsSearchResultAsync(any(), any(), any(), any()) } throws Exception()
        runBlocking { subject.getArtistsSearchResult("", 0) }
    }

    @Test
    fun `getArtistInfo() - returns artist info`() {
        every { api.getArtistInfoResultAsync(any(), any(), any(), any()) } returns deferredArtistInfoResult
        coEvery { deferredArtistInfoResult.await() } returns artistInfoResponse
        every { artistInfoResponseMapper.mapFromArtistInfoResponse(artistInfoResponse) } returns SOME_ARTIST_INFO

        val actualArtistInfo = runBlocking { subject.getArtistInfo("") }

        assertThat(actualArtistInfo).isEqualTo(SOME_ARTIST_INFO)
    }

    @Test(expected = Exception::class)
    fun `getArtistInfo() - throws Exception if api is not successful`() {
        every { api.getArtistInfoResultAsync(any(), any(), any(), any()) } throws Exception()
        runBlocking { subject.getArtistInfo("") }
    }

}