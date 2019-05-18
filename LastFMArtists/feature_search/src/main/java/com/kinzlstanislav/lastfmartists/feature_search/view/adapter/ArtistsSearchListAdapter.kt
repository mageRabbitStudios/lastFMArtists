package com.kinzlstanislav.lastfmartists.feature_search.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kinzlstanislav.lastfmartists.feature_search.R
import com.kinzlstanislav.lastfmartists.architecture.core.model.Artist
import com.kinzlstanislav.lastfmartists.base.imageloading.GlideImageLoader
import com.kinzlstanislav.lastfmartists.base.inflateViewForHolder

class ArtistsSearchListAdapter(
    private val imageLoader: GlideImageLoader,
    private val itemClickListener: ArtistItemClickListener
) : RecyclerView.Adapter<ArtistViewHolder>() {

    private var artistList: MutableList<Artist> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder
        = ArtistViewHolder(
        inflateViewForHolder(
            parent,
            R.layout.item_artist_tile
        ), imageLoader, itemClickListener)

    override fun getItemCount() = artistList.size

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(artistList[position])
    }

    fun updateItems(artists: List<Artist>) {
        artistList.clear()
        artistList.addAll(artists)
        notifyDataSetChanged()
    }

}