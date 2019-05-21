package com.kinzlstanislav.lastfmartists.feature_search.viewmodel

import com.kinzlstanislav.lastfmartists.architecture.core.model.Artist
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchLastfmArtistsByNameUseCase
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchLastfmArtistsByNameUseCase.Result
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchLastfmArtistsByNameUseCase.Result.*
import com.kinzlstanislav.lastfmartists.feature_search.viewmodel.SearchViewModel.FragmentSearchState
import com.kinzlstanislav.lastfmartists.feature_search.viewmodel.SearchViewModel.FragmentSearchState.*
import com.kinzlstanislav.lastfmartists.unittesting.BaseViewModelTest
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class SearchViewModelImplTest : BaseViewModelTest<FragmentSearchState>() {

    var mockReturnedArtistsResult = mockk<List<Artist>>()
    var mockFetchLastfmArtistByNameUseCase = mockk<FetchLastfmArtistsByNameUseCase>()
    var subject = SearchViewModelImpl(testAppCoroutineScope, testState, mockFetchLastfmArtistByNameUseCase)

    @Test
    fun `fetchLastfmArtistsSuggestions()`() {
        assertStateWhenFetchLastfmArtistsSugg(
            Success(mockReturnedArtistsResult),
            ArtistsLoaded(mockReturnedArtistsResult)
        )
        assertStateWhenFetchLastfmArtistsSugg(NetworkError, FetchingArtistsNE)
        assertStateWhenFetchLastfmArtistsSugg(GenericError, FetchingArtistsGE)
        verify(exactly = 3) { testState.value = LoadingArtists }
    }

    private fun assertStateWhenFetchLastfmArtistsSugg(result: Result, expectedState: FragmentSearchState) {
        givenFetchLastfmArtistByNameUseCaseReturns(result)
        whenFetchLastfmArtistsSuggestions()
        thenStateShouldBe(expectedState)
    }

    private fun givenFetchLastfmArtistByNameUseCaseReturns(result: Result) = coEvery {
        mockFetchLastfmArtistByNameUseCase.execute(any(), any())
    } returns result

    private fun whenFetchLastfmArtistsSuggestions() {
        subject.fetchLastfmArtistsSuggestions("asd", 123)
    }
}