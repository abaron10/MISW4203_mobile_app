package com.example.vinilos.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilos.models.Album
import com.example.vinilos.network.AlbumServiceAdapter

class AlbumRepository (val application: Application){
    fun refreshData(callback: (List<Album>)->Unit, onError: (VolleyError)->Unit) {
        AlbumServiceAdapter.getInstance(application).getAlbums({
            callback(it)
        }, onError)
    }

    fun refreshAlbum(albumId: Int, callback: (Album)->Unit, onError: (VolleyError) -> Unit) {
        AlbumServiceAdapter.getInstance(application).getAlbum(albumId, {
            callback(it)
        }, onError)
    }

    fun addAlbum(albumParams: Map<String, String>, callback: (Album) -> Unit, onError: (VolleyError) -> Unit) {
        AlbumServiceAdapter.getInstance(application).createAlbum(albumParams, {
            callback(it)
        }, onError)
    }
}