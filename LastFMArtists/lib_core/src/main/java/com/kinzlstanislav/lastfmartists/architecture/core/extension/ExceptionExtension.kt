package com.kinzlstanislav.lastfmartists.architecture.core.extension

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException

fun Exception.isConnectionError(): Boolean {
    return this is SocketTimeoutException ||
            this is ConnectException ||
            this is UnknownHostException
}

/**
 * Throws stopping the coroutine immediately
 * */
fun Exception.throwIfCancellationException() {
    if (this is CancellationException) throw this
}