package com.kinzlstanislav.lastfmartists.architecture.domain

import com.kinzlstanislav.lastfmartists.architecture.core.model.ArtistInfo
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchArtistInfoUseCase.Result
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchArtistInfoUseCase.Result.*
import com.kinzlstanislav.lastfmartists.architecture.repository.LastfmArtistRepository
import com.kinzlstanislav.lastfmartists.unittesting.BaseUseCaseTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FetchArtistInfoUseCaseTest : BaseUseCaseTest<Result>() {

    var mockRepository = mockk<LastfmArtistRepository>()
    var mockArtistInfo = mockk<ArtistInfo>()
    var subject = FetchArtistInfoUseCase(testAppCoroutineScope, mockRepository)

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
        thenResultIs(Success(mockArtistInfo))
    }

    private fun whenUseCaseExecuted() = runBlocking { useCaseResult = subject.execute("") }

    private fun givenRepositoryThrows(exception: Exception) = coEvery {
        mockRepository.getArtistInfo(any()) } throws exception

    private fun givenRepositoryReturns() = coEvery {
        mockRepository.getArtistInfo(any()) } returns mockArtistInfo

}