package com.kinzlstanislav.lastfmartists.feature_search.injection

import androidx.lifecycle.ViewModelProviders
import com.kinzlstanislav.lastfmartists.feature_search.view.FragmentSearch
import com.kinzlstanislav.lastfmartists.feature_search.viewmodel.SearchViewModel
import com.kinzlstanislav.lastfmartists.feature_search.viewmodel.SearchViewModelFactory
import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.lastfmartists.architecture.core.dagger.scopes.PerFragment
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchLastfmArtistsByNameUseCase
import dagger.Module
import dagger.Provides

@Module
class FragmentSearchModule {

    @Provides
    @PerFragment
    fun provideSearchViewModel(
        fragment: FragmentSearch,
        factory: SearchViewModelFactory
    ) = ViewModelProviders.of(fragment, factory)
        .get(SearchViewModel::class.java)

    @Provides
    @PerFragment
    fun provideSearchViewModelFactory(
        appCoroutineScope: AppCoroutineScope,
        fetchLastfmArtistsByNameUseCase: FetchLastfmArtistsByNameUseCase
    ) = SearchViewModelFactory(appCoroutineScope, fetchLastfmArtistsByNameUseCase)

}