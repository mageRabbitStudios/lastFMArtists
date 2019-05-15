package com.kinzlstanislav.lastfmartists.feature_artist_detail.injection

import androidx.lifecycle.ViewModelProviders
import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.lastfmartists.architecture.core.dagger.scopes.PerFragment
import com.kinzlstanislav.lastfmartists.architecture.domain.FetchArtistInfoUseCase
import com.kinzlstanislav.lastfmartists.feature_artist_detail.view.FragmentArtistDetail
import com.kinzlstanislav.lastfmartists.feature_artist_detail.viewmodel.ArtistDetailViewModel
import com.kinzlstanislav.lastfmartists.feature_artist_detail.viewmodel.ArtistDetailViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class FragmentArtistDetailModule {

    @Provides
    @PerFragment
    fun provideArtistDetailViewModel(
        fragment: FragmentArtistDetail,
        factory: ArtistDetailViewModelFactory
    ) = ViewModelProviders.of(fragment, factory).get(ArtistDetailViewModel::class.java)

    @Provides
    @PerFragment
    fun provideArtistDetailViewModelFactory(
        appCoroutineScope: AppCoroutineScope,
        fetchArtistInfoUseCase: FetchArtistInfoUseCase
    ) = ArtistDetailViewModelFactory(appCoroutineScope, fetchArtistInfoUseCase)

}
