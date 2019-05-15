package com.kinzlstanislav.lastfmartists.architecture.core.extension

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