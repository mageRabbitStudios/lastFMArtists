package com.kinzlstanislav.lastfmartists.unittesting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import io.mockk.spyk
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.rules.TestRule

abstract class BaseViewModelTest<VM_STATE : Any> : BaseCoroutineTest() {

    @get:Rule
    internal var rule: TestRule = InstantTaskExecutorRule()

    protected var testState = spyk<MutableLiveData<VM_STATE>>()

    protected fun thenStateShouldBe(state: VM_STATE) {
        assertThat(testState.value).isEqualTo(state)
    }

}