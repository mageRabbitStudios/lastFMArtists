package com.kinzlstanislav.lastfmartists.architecture.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Artist(
    val name: String,
    val imageUrl: String
) : Parcelable