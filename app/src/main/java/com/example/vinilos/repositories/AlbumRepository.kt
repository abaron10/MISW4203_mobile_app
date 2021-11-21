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

    fun addAlbum(albumParams: Map<String, String>, callback: (Album) -> Unit, onError: (VolleyError) -> Unit) {
        AlbumServiceAdapter.getInstance(application).createAlbum(albumParams, {
            callback(it)
        }, onError)
        val db = VinylRoomDatabase.getDatabase(application.applicationContext)
        // Aqui se tiene que llamar el borrado de la base de datos de room -> db.albumDao().deleteAll()
    }
}