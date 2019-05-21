package com.kinzlstanislav.lastfmartists.feature_search.view

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.kinzlstanislav.lastfmartists.architecture.core.model.Artist
import com.kinzlstanislav.lastfmartists.feature_search.R
import com.kinzlstanislav.lastfmartists.feature_search.viewmodel.SearchViewModel
import com.kinzlstanislav.lastfmartists.feature_search.viewmodel.SearchViewModel.FragmentSearchState
import com.kinzlstanislav.lastfmartists.feature_search.viewmodel.SearchViewModel.FragmentSearchState.*
import com.kinzlstanislav.lastfmartists.viewtesting.Robolectric4FragmentDaggerTest
import com.kinzlstanislav.lastfmartists.viewtesting.matchers.isGone
import com.kinzlstanislav.lastfmartists.viewtesting.matchers.isVisible
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertContains
import com.schibsted.spain.barista.interaction.BaristaClickInteractions.clickOn
import com.schibsted.spain.barista.interaction.BaristaListInteractions.clickListItem
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test
import java.nio.CharBuffer

private val SOME_ARTISTS = listOf(
    Artist(name = "Mario Puzo", id = "123", imageUrl = "nourl"),
    Artist(name = "Godzilla", id = "123", imageUrl = "nourl"),
    Artist(name = "Thrall", id = "123", imageUrl = "nourl")
)

class FragmentSearchTest : Robolectric4FragmentDaggerTest<FragmentSearch>() {

    var mockViewModel = mockk<SearchViewModel>(relaxed = true)
    val subjectState = spyk<MutableLiveData<FragmentSearchState>>()

    override var injector: (FragmentSearch) -> Unit = {
        it.viewModel = mockViewModel
        it.imageLoader = mockk(relaxed = true)
        every { it.viewModel.searchState } returns subjectState
    }

    override val subject = FragmentSearch()

    override fun setup() {
        super.setup()
        launchFragment(subject, injector)
    }

    @Test
    fun assertViewsOnFragmentStartAreDisplayed() {
        assertWelcomeMessageContains(
            "It's time to start looking for some artists! Click " +
                    "the magnifying glass above to do that."
        )
        assertToolbarTitleContains("Search for your favorite artists")
        assertSearchViewHintIs("Search for artists... (eg Stanislav)")
    }

    @Test
    fun searchFlow() {
        // performing search + loading state checks
        givenEveryFetchLastfmArtistsSuggestionsSetsLoading()
        givenIClickOnSearchButton()
        whenITypeThisIntoSearchBox("") // checks that artists_search.query.isNotEmpty() returns false
        whenITypeThisIntoSearchBox("test")
        verifyFetchLastfmArtistsSuggestionsWasCalled(times = 4)
        verifyLoadingWasSet(times = 4)
        andListShouldBeHidden()
        andLoaderVisible()

        // getting results
        whenStateIs(ArtistsLoaded(SOME_ARTISTS))
        thenFlipperForStatesShouldBeHidden()
        andListOfArtistsVisible()
        assertArtistItem(0, "Mario Puzo")
        assertArtistItem(1, "Godzilla")
        assertArtistItem(2, "Thrall")

        // clicking on item and navigating to detail
        whenIClickSecondItemOfTheList()
        thenIShouldNavigateToArtistDetail()
    }

    @Test
    fun whenUserClicksOnBackButton_thenAppCloses() {
        whenUserClicksBackButton()
        thenActivityIsFinishing()
    }

    @Test
    fun whenNetworkError_thenISeeNEView_andCanClickRefreshButton() {
        givenIClickOnSearchButton()
        whenITypeThisIntoSearchBox("test")

        whenStateIs(FetchingArtistsNE)
        thenISeeNetworkErrorText("This is a NETWORK error view")
        withNetworkErrorButtonText("Refresh!")
        andListShouldBeHidden()

        clickOn(R.id.network_error_refresh_button)
        verifyFetchLastfmArtistsSuggestionsWasCalled(times = 5) // 4 times for "test" input, once more for clicking ref.
    }

    @Test
    fun whenGenericError_thenISeeGEView_andCanClickRefreshButton() {
        givenIClickOnSearchButton()
        whenITypeThisIntoSearchBox("a")

        whenStateIs(FetchingArtistsGE)
        thenISeeGenericErrorText("This is a GENERIC error view")
        withGenericErrorButtonText("Refresh!")
        andListShouldBeHidden()

        clickOn(R.id.network_error_refresh_button)
        verifyFetchLastfmArtistsSuggestionsWasCalled(times = 2) // once for "a" input, once more for clicking ref.
    }

    private fun givenEveryFetchLastfmArtistsSuggestionsSetsLoading() {
        every { mockViewModel.fetchLastfmArtistsSuggestions(any(), any()) } answers { subjectState.value = LoadingArtists }
    }

    private fun verifyFetchLastfmArtistsSuggestionsWasCalled(times: Int) {
        verify(exactly = times) { mockViewModel.fetchLastfmArtistsSuggestions(any(), any()) }
    }

    private fun verifyLoadingWasSet(times: Int) {
        verify(exactly = times) { subjectState.value = LoadingArtists }
    }

    private fun givenIClickOnSearchButton() {
        onView(withId(androidx.appcompat.R.id.search_button)).perform(click())
    }

    private fun whenITypeThisIntoSearchBox(text: String) {
        onView(withId(androidx.appcompat.R.id.search_src_text)).perform(typeText(text))
    }

    private fun whenStateIs(state: FragmentSearchState) {
        subjectState.value = state
    }

    private fun whenIClickSecondItemOfTheList() {
        clickListItem(R.id.artists_list_recycler_view, 1)
    }

    private fun whenUserClicksBackButton() {
        onView(ViewMatchers.isRoot()).perform(ViewActions.pressBack())
    }

    private fun assertWelcomeMessageContains(text: String) {
        assertContains(R.id.search_screen_welcome, text)
    }

    private fun assertToolbarTitleContains(text: String) {
        assertContains(R.id.search_toolbar_title, text)
    }

    private fun assertSearchViewHintIs(text: String) {
        val searchViewEditBox = getActivity()
            .findViewById<SearchView.SearchAutoComplete>(androidx.appcompat.R.id.search_src_text)
        assertEquals(
            CharBuffer.wrap(searchViewEditBox.hint),
            CharBuffer.wrap("   $text")
        )
    }

    private fun assertArtistItem(position: Int, name: String) {
        assertDisplayedAtPosition(R.id.artists_list_recycler_view, position, R.id.item_artist_name, name)
    }

    private fun andListShouldBeHidden() {
        R.id.artists_list_recycler_view.isGone()
    }

    private fun andLoaderVisible() {
        R.id.contributors_list_loader.isVisible()
    }

    private fun andListOfArtistsVisible() {
        R.id.artists_list_recycler_view.isVisible()
    }

    private fun thenFlipperForStatesShouldBeHidden() {
        R.id.flipper_search.isGone()
    }

    private fun thenIShouldNavigateToArtistDetail() {
        verify(exactly = 1) { mockNavController.navigate(R.id.search_to_artist_detail, any()) }
    }

    private fun thenActivityIsFinishing() {
        Assert.assertTrue(subject.requireActivity().isFinishing)
    }

    private fun withNetworkErrorButtonText(text: String) {
        assertContains(R.id.network_error_refresh_button, text)
    }

    private fun withGenericErrorButtonText(text: String) {
        assertContains(R.id.generic_error_refresh_button, text)
    }

    private fun thenISeeNetworkErrorText(text: String) {
        assertContains(R.id.network_error_text, text)
    }

    private fun thenISeeGenericErrorText(text: String) {
        assertContains(R.id.generic_error_text, text)
    }
}