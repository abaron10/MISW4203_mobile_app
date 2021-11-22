package com.example.vinilos.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilos.database.AlbumDAO
import com.example.vinilos.database.VinylRoomDatabase
import com.example.vinilos.models.Album
import com.example.vinilos.network.AlbumServiceAdapter

class AlbumRepository (val application: Application){
    suspend fun refreshData(): List<Album> {
        val db = VinylRoomDatabase.getDatabase(application.applicationContext)
        val cached = db.albumDao().getAlbums()
        return if(cached.isNullOrEmpty()) {
            val albums = AlbumServiceAdapter.getInstance(application).getAlbums()
            for(album in albums) {
                db.albumDao().insert(album)
            }
            albums
        } else {
            cached
        }
    }

    suspend fun refreshAlbum(albumId: Int): Album {
        val db = VinylRoomDatabase.getDatabase(application.applicationContext)
        val albumsWithId = db.albumDao().getAlbum(albumId)
        return if(albumsWithId.isNullOrEmpty()) {
            AlbumServiceAdapter.getInstance(application).getAlbum(albumId)
        } else {
            albumsWithId.first()
        }
    }

    suspend fun addAlbum(albumParams: Map<String, String>) {
        AlbumServiceAdapter.getInstance(application).createAlbum(albumParams)
        val db = VinylRoomDatabase.getDatabase(application.applicationContext)
        db.albumDao().deleteAll()
    }
}