package com.kinzlstanislav.lastfmartists.injection

import android.app.Application
import com.kinzlstanislav.lastfmartists.LastfmArtistsApplication
import com.kinzlstanislav.lastfmartists.injection.bindings.ActivityBindings
import com.kinzlstanislav.lastfmartists.injection.bindings.FragmentBindings
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication

@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBindings::class,
    FragmentBindings::class,
    ApplicationModule::class
])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: LastfmArtistsApplication)
}
