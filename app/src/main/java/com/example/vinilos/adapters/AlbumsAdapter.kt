package com.example.vinilos.adapters

import com.example.vinilos.R
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.databinding.AlbumItemLayoutBinding
import com.example.vinilos.models.Album
import com.example.vinilos.ui.AlbumDetailActivity
import com.example.vinilos.ui.BaseActivity

class AlbumsAdapter(var context: Context) : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

  var albums :List<Album> = emptyList()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
    val withDataBinding: AlbumItemLayoutBinding = DataBindingUtil.inflate(
      LayoutInflater.from(parent.context),
      AlbumViewHolder.LAYOUT,
      parent,
      false)
    return AlbumViewHolder(withDataBinding)
  }

  override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
    holder.viewDataBinding.also {
      it.album = albums[position]
    }
    holder.viewDataBinding.root.setOnClickListener {
      val intent = Intent(context, AlbumDetailActivity::class.java)
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      intent.putExtra(
        BaseActivity.INTENT_EXTRA_ALBUM_ID, albums[position].albumId
      )
      context.startActivity(intent)
    }
  }

  override fun getItemCount(): Int {
    return albums.size
  }

  class AlbumViewHolder(val viewDataBinding: AlbumItemLayoutBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
      @LayoutRes
      val LAYOUT = R.layout.album_item_layout
    }
  }
}