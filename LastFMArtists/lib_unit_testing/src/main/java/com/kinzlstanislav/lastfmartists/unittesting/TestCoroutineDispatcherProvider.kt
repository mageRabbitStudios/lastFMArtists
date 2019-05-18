package com.kinzlstanislav.lastfmartists.unittesting

import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

class TestCoroutineDispatcherProvider : DispatcherProvider {

    @ExperimentalCoroutinesApi
    override val background: CoroutineDispatcher
        get() = Dispatchers.Unconfined

    @ExperimentalCoroutinesApi
    override val io: CoroutineDispatcher
        get() = Dispatchers.Unconfined

    @ExperimentalCoroutinesApi
    override val ui: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}