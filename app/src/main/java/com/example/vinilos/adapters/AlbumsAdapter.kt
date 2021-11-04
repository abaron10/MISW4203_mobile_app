package com.example.vinilos.adapters

import com.example.vinilos.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.models.Album

class AlbumsAdapter(var context: Context) : RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

  var dataList = emptyList<Album>()

  internal fun setDataList(dataList: List<Album>) {
    this.dataList = dataList
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var albumImage: ImageView
    var albumName: TextView

    init {
      albumImage = itemView.findViewById(R.id.album_item_image)
      albumName = itemView.findViewById(R.id.album_item_name)
      itemView.setOnClickListener {
        Toast.makeText(it.context, "Buenas", Toast.LENGTH_SHORT).show()
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsAdapter.ViewHolder {
    var view = LayoutInflater.from(parent.context).inflate(R.layout.album_item_layout, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: AlbumsAdapter.ViewHolder, position: Int) {
    var data = dataList[position]
    holder.albumName.text = data.description
  }

  override fun getItemCount() = dataList.size
}