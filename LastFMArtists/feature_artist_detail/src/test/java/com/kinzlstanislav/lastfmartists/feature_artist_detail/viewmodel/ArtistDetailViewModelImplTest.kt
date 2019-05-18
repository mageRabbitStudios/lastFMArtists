package com.kinzlstanislav.lastfmartists.feature_artist_detail.viewmodel

import com.kinzlstanislav.lastfmartists.architecture.core.model.ArtistInfo
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchArtistInfoUseCase
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchArtistInfoUseCase.Result
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchArtistInfoUseCase.Result.*
import com.kinzlstanislav.lastfmartists.feature_artist_detail.viewmodel.ArtistDetailViewModel.ArtistDetailInfoState
import com.kinzlstanislav.lastfmartists.feature_artist_detail.viewmodel.ArtistDetailViewModel.ArtistDetailInfoState.*
import com.kinzlstanislav.lastfmartists.unittesting.BaseViewModelTest
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class ArtistDetailViewModelImplTest : BaseViewModelTest<ArtistDetailInfoState>() {

    var mockFetchArtistInfoUseCase = mockk<FetchArtistInfoUseCase>()
    var mockArtistInfo = mockk<ArtistInfo>()
    var subject = ArtistDetailViewModelImpl(testAppCoroutineScope, testState, mockFetchArtistInfoUseCase)

    @Test fun `fetchLastfmArtistById()`() {
        assertStateWhenFetchLastfmArtistById(Success(mockArtistInfo), ArtistInfoLoaded(mockArtistInfo))
        assertStateWhenFetchLastfmArtistById(GenericError, FetchingArtistInfoGE)
        assertStateWhenFetchLastfmArtistById(NetworkError, FetchingArtistInfoNE)
        verify(exactly = 3) { testState.value = LoadingArtistInfo }
    }

    private fun assertStateWhenFetchLastfmArtistById(result: Result, expectedState: ArtistDetailInfoState) {
        givenFetchArtistInfoUseCaseReturns(result)
        whenFetchLastfmArtistById()
        thenStateShouldBe(expectedState)
    }

    private fun givenFetchArtistInfoUseCaseReturns(result: Result) = coEvery {
        mockFetchArtistInfoUseCase.execute(any()) } returns result

    private fun whenFetchLastfmArtistById() {
        subject.fetchLastfmArtistById("123")
    }

}