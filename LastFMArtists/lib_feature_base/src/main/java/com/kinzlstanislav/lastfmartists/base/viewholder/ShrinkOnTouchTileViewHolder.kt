package com.kinzlstanislav.lastfmartists.base.viewholder

interface ShrinkOnTouchTileViewHolder {

    var touchUpAction: () -> Unit

    fun shrink()

    fun shrinkBack()
}
