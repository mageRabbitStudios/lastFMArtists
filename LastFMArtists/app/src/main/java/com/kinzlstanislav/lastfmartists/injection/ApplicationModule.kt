package com.kinzlstanislav.lastfmartists.injection

import android.app.Application
import android.content.Context
import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AndroidDispatcherProvider
import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AppCoroutineScope
import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.AppCoroutineScopeImpl
import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.DispatcherProvider
import com.kinzlstanislav.lastfmartists.architecture.core.dagger.qualifiers.ForApplicationContext
import com.kinzlstanislav.lastfmartists.architecture.network.injection.NetworkModule
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class])
class ApplicationModule {

    @Provides
    fun provideAppCoroutineScope(dispatcherProvider: DispatcherProvider): AppCoroutineScope = AppCoroutineScopeImpl(dispatcherProvider)

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = AndroidDispatcherProvider()

    @Provides
    @ForApplicationContext
    fun provideContext(application: Application): Context = application

}