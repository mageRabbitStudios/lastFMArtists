package com.kinzlstanislav.lastfmartists.injection.bindings

import com.kinzlstanislav.lastfmartists.architecture.core.dagger.scopes.PerActivity
import com.kinzlstanislav.lastfmartists.view.ApplicationActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBindings {

    @PerActivity
    @ContributesAndroidInjector
    fun bindMainActivity(): ApplicationActivity

}