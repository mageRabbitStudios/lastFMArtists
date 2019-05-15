package com.kinzlstanislav.lastfmartists.base.extension

import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment


fun Fragment.showToast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, length).show()
}

fun Fragment.disableTouch() {
    requireActivity().window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun Fragment.enableTouch() {
    requireActivity().window.clearFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

/**
 * Useful when performing actions on views that cannot be performed actions on because
 * not working synthetic extensions (view is in other module for example)
 */
fun Fragment.fromId(id: Int): View = requireActivity().findViewById(id)

fun Fragment.onClickOn(id: Int, doOnClick: () -> Unit) {
    fromId(id).setOnClickListener { doOnClick() }
}