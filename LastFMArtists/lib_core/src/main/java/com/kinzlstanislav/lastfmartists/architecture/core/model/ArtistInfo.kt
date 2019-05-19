package com.kinzlstanislav.lastfmartists.architecture.core.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ArtistInfo(
    val published: String,
    val summary: String,
    val content: String
)

@Parcelize
data class ArtistAvatarBitmap(
    val bitmap: Bitmap?
) : Parcelable