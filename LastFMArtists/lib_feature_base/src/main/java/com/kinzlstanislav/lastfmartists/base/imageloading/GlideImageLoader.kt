package com.kinzlstanislav.lastfmartists.base.imageloading

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.widget.ImageView
import com.kinzlstanislav.lastfmartists.base.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import javax.inject.Inject

class GlideImageLoader @Inject constructor() {

    fun loadFromUrl(context: Context, url: String, target: ImageView) {
        Glide.with(context)
            .applyDefaultRequestOptions(RequestOptions()
                .centerCrop())
            .load(url)
            .transition(withCrossFade())
            .into(target)
    }

}