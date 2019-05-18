package com.kinzlstanislav.lastfmartists.unittesting

import androidx.annotation.CallSuper
import com.kinzlstanislav.lastfmartists.architecture.core.extension.isConnectionError
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import java.io.IOException

abstract class BaseUseCaseTest<RESULT: Any> : BaseCoroutineTest() {

    private companion object {
        const val EXCEPTION_EXTENSION_KT_FILE_REF =
            "com.kinzlstanislav.lastfmartists.architecture.core.extension.ExceptionExtensionKt"
    }

    @Before
    @CallSuper
    open fun before() {
        // enables mocking static kotlin extension functions at the file
        mockkStatic(EXCEPTION_EXTENSION_KT_FILE_REF)
    }

    protected lateinit var useCaseResult: RESULT

    protected fun thenResultIs(expectedResult: RESULT) {
        assertThat(useCaseResult).isEqualTo(expectedResult)
    }

    protected var mockException = mockk<IOException>()

    protected fun givenIsConnectionExceptionReturns(isConnectionException: Boolean)=
        every { mockException.isConnectionError() } returns isConnectionException

}