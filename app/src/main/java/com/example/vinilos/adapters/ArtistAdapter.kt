package com.example.vinilos.adapters

import android.annotation.SuppressLint
import com.example.vinilos.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.databinding.ArtistItemLayoutBinding
import com.example.vinilos.models.Artist

class ArtistAdapter(var context: Context) : RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>() {

  var artist :List<Artist> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
    val withDataBinding: ArtistItemLayoutBinding = DataBindingUtil.inflate(
      LayoutInflater.from(parent.context),
      ArtistViewHolder.LAYOUT,
      parent,
      false)
    return ArtistViewHolder(withDataBinding)
  }

  override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
    holder.viewDataBinding.also {
      it.artist = artist[position]
    }
    holder.viewDataBinding.root.setOnClickListener {
      Toast.makeText(it.context, artist[position].name, Toast.LENGTH_SHORT).show()
    }
  }

  override fun getItemCount(): Int {
    return artist.size
  }

  class ArtistViewHolder(val viewDataBinding: ArtistItemLayoutBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
      @LayoutRes
      val LAYOUT = R.layout.artist_item_layout
    }
  }
}