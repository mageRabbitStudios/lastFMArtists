package com.kinzlstanislav.feature_search.injection

import androidx.lifecycle.ViewModelProviders
import com.kinzlstanislav.feature_search.view.FragmentSearch
import com.kinzlstanislav.feature_search.viewmodel.FragmentSearchViewModel
import com.kinzlstanislav.feature_search.viewmodel.FragmentSearchViewModelFactory
import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.lastfmartists.architecture.core.dagger.scopes.PerFragment
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchLastfmArtistsByNameUseCase
import dagger.Module
import dagger.Provides

@Module
class FragmentSearchModule {

    @Provides
    @PerFragment
    fun provideFragmentSearchViewModel(
        fragment: FragmentSearch,
        factory: FragmentSearchViewModelFactory
    ) = ViewModelProviders.of(fragment, factory)
        .get(FragmentSearchViewModel::class.java)

    @Provides
    @PerFragment
    fun provideFragmentSearchViewModelFactory(
        appCoroutineScope: AppCoroutineScope,
        fetchLastfmArtistsByNameUseCase: FetchLastfmArtistsByNameUseCase
    ) = FragmentSearchViewModelFactory(appCoroutineScope, fetchLastfmArtistsByNameUseCase)

}