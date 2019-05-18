package com.kinzlstanislav.lastfmartists.feature_search.view.adapter

import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.kinzlstanislav.lastfmartists.architecture.core.model.Artist
import com.kinzlstanislav.lastfmartists.architecture.core.model.ArtistAvatarBitmap
import com.kinzlstanislav.lastfmartists.base.imageloading.GlideImageLoader
import com.kinzlstanislav.lastfmartists.base.viewholder.ShrinkOnTouchTileViewHolder
import com.kinzlstanislav.lastfmartists.base.viewholder.ShrinkOnTouchTileViewHolderImpl
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_artist_tile.*

class ArtistViewHolder(
    override val containerView: View,
    private val imageLoader: GlideImageLoader,
    private val itemOnClickListener: ArtistItemClickListener
) : RecyclerView.ViewHolder(containerView), LayoutContainer,
    ShrinkOnTouchTileViewHolder by ShrinkOnTouchTileViewHolderImpl(containerView) {

    fun bind(artist: Artist) {
        item_artist_name.text = artist.name
        imageLoader.loadFromUrl(containerView.context, artist.imageUrl, item_img_artist)
        touchUpAction = {
            val avatarBitmap = ArtistAvatarBitmap(item_img_artist.drawable.toBitmap())
            itemOnClickListener.onArtistItemClicked(artist, avatarBitmap)
        }
    }

}