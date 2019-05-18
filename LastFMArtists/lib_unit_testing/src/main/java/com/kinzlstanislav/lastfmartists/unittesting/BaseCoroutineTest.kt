package com.kinzlstanislav.lastfmartists.unittesting

abstract class BaseCoroutineTest {

    protected val testAppCoroutineScope = TestAppCoroutineScope(TestCoroutineDispatcherProvider())
}