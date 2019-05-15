package com.kinzlstanislav.lastfmartists.injection.bindings

import com.kinzlstanislav.feature_search.injection.FragmentSearchModule
import com.kinzlstanislav.feature_search.view.FragmentSearch
import com.kinzlstanislav.lastfmartists.architecture.core.dagger.scopes.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBindings {

    @PerFragment
    @ContributesAndroidInjector(modules = [FragmentSearchModule::class])
    fun bindSearchFragment(): FragmentSearch

}