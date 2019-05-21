package com.kinzlstanislav.lastfmartists.base.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ViewFlipper

class StateViewFlipper(
    context: Context, attrs: AttributeSet?
) : ViewFlipper(context, attrs) {

    init {
        visibility = View.GONE
    }

    fun show(view: View) {
        if (visibility == View.GONE) {
            visibility = View.VISIBLE
        }

        if (indexOfChild(view) == -1) {
            addView(view)
        }

        if (currentView != view) {
            // Flip to specific view
            displayedChild = indexOfChild(view)
        }
    }
}