package com.kinzlstanislav.lastfmartists.base.extension

import androidx.appcompat.widget.SearchView

fun <S : SearchView> S.doOnSearch(submit: S.() -> Unit = {}, change: S.() -> Unit = {}) {
    setOnQueryTextListener(object: SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            submit()
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            change()
            return false
        }
    })
}