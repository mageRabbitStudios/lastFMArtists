package com.kinzlstanislav.lastfmartists.base.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> Fragment.observe(liveData: LiveData<T>, body: (T) -> Unit = {}) {
    liveData.observe(this.viewLifecycleOwner, Observer { body(it) })
}