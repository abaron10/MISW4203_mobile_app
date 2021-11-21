package com.example.vinilos.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilos.models.Album
import com.example.vinilos.network.AlbumServiceAdapter

class AlbumRepository (val application: Application){
    suspend fun refreshData(): List<Album> {
        return AlbumServiceAdapter.getInstance(application).getAlbums()
    }

    suspend fun refreshAlbum(albumId: Int): Album {
        return AlbumServiceAdapter.getInstance(application).getAlbum(albumId)
    }

    fun addAlbum(albumParams: Map<String, String>, callback: (Album) -> Unit, onError: (VolleyError) -> Unit) {
        AlbumServiceAdapter.getInstance(application).createAlbum(albumParams, {
            callback(it)
        }, onError)
    }
}