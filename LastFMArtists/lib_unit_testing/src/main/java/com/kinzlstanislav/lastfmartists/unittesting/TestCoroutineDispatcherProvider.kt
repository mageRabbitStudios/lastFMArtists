package com.kinzlstanislav.lastfmartists.unittesting

import com.kinzlstanislav.lastfmartists.architecture.core.coroutines.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TestCoroutineDispatcherProvider : DispatcherProvider {

    override val background: CoroutineDispatcher
        get() = Dispatchers.Unconfined

    override val io: CoroutineDispatcher
        get() = Dispatchers.Unconfined

    override val ui: CoroutineDispatcher
        get() = Dispatchers.Unconfined
}