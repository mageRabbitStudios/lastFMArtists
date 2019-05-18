package com.kinzlstanislav.lastfmartists.architecture.domain

import com.kinzlstanislav.lastfmartists.architecture.core.model.Artist
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchLastfmArtistsByNameUseCase.Result
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchLastfmArtistsByNameUseCase.Result.*
import com.kinzlstanislav.lastfmartists.architecture.repository.LastfmArtistRepository
import com.kinzlstanislav.lastfmartists.unittesting.BaseUseCaseTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FetchLastfmArtistsByNameUseCaseTest : BaseUseCaseTest<Result>() {

    var mockRepository = mockk<LastfmArtistRepository>()
    var mockArtistList = mockk<List<Artist>>()
    var subject = FetchLastfmArtistsByNameUseCase(testAppCoroutineScope, mockRepository)

    @Test fun `execute() - NetworkError`() {
        givenRepositoryThrows(mockException)
        givenIsConnectionExceptionReturns(true)
        whenUseCaseExecuted()
        thenResultIs(NetworkError)
    }

    @Test fun `execute() - GenericError`() {
        givenRepositoryThrows(mockException)
        givenIsConnectionExceptionReturns(false)
        whenUseCaseExecuted()
        thenResultIs(GenericError)
    }

    @Test fun `execute() - Success`() {
        givenRepositoryReturns()
        whenUseCaseExecuted()
        thenResultIs(Success(mockArtistList))
    }

    private fun whenUseCaseExecuted() = runBlocking { useCaseResult = subject.execute("", 0)}

    private fun givenRepositoryThrows(exception: Exception) = coEvery {
        mockRepository.getArtistsSearchResult(any(), any()) } throws exception

    private fun givenRepositoryReturns() = coEvery {
        mockRepository.getArtistsSearchResult(any(), any()) } returns mockArtistList

}