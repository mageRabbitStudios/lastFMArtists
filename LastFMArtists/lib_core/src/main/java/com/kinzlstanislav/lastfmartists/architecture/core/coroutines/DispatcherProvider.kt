package com.kinzlstanislav.lastfmartists.architecture.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val background: CoroutineDispatcher

    val io: CoroutineDispatcher

    val ui: CoroutineDispatcher
}