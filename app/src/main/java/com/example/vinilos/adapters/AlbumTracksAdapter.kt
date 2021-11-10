package com.example.vinilos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.AlbumTrackItemLayoutBinding
import com.example.vinilos.models.Track

class AlbumTracksAdapter(var context: Context): RecyclerView.Adapter<AlbumTracksAdapter.AlbumTrackViewHolder>() {
    var tracks: List<Track> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumTrackViewHolder {
        val withDataBinding: AlbumTrackItemLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumTracksAdapter.AlbumTrackViewHolder.LAYOUT,
            parent,
            false)
        return AlbumTracksAdapter.AlbumTrackViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumTrackViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.track = tracks[position]
        }

    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    class AlbumTrackViewHolder(val viewDataBinding: AlbumTrackItemLayoutBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_track_item_layout
        }
    }
}