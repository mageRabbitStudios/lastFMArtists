package com.kinzlstanislav.lastfmartists.injection.bindings

import com.kinzlstanislav.lastfmartists.feature_search.injection.FragmentSearchModule
import com.kinzlstanislav.lastfmartists.feature_search.view.FragmentSearch
import com.kinzlstanislav.lastfmartists.architecture.core.dagger.scopes.PerFragment
import com.kinzlstanislav.lastfmartists.feature_artist_detail.injection.FragmentArtistDetailModule
import com.kinzlstanislav.lastfmartists.feature_artist_detail.view.FragmentArtistDetail
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBindings {

    @PerFragment
    @ContributesAndroidInjector(modules = [FragmentSearchModule::class])
    fun bindSearchFragment(): FragmentSearch

    @PerFragment
    @ContributesAndroidInjector(modules = [FragmentArtistDetailModule::class])
    fun bindArtistDetailFragment(): FragmentArtistDetail

}