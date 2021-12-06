package com.example.vinilos.repositories

import android.app.Application
import com.example.vinilos.database.VinylRoomDatabase
import com.example.vinilos.models.Album
import com.example.vinilos.models.Artist
import com.example.vinilos.network.AlbumServiceAdapter
import com.example.vinilos.network.ArtistServiceAdapter

class ArtistRepository (val application: Application){
    suspend fun refreshData(): List<Artist> {
        val db = VinylRoomDatabase.getDatabase(application.applicationContext)
        val cached = db.artistDao().getArtists()
        return if(cached.isNullOrEmpty()) {
            val artists = ArtistServiceAdapter.getInstance(application).getArtists()
            for(artist in artists) {
                db.artistDao().insert(artist)
            }
            artists
        } else {
            cached
        }
    }

    suspend fun refreshArtist(artistId: Int): Artist {
        val db = VinylRoomDatabase.getDatabase(application.applicationContext)
        val artistsWithId = db.artistDao().getArtist(artistId)
        return if(artistsWithId.isNullOrEmpty()) {
            ArtistServiceAdapter.getInstance(application).getArtist(artistId)
        } else {
            artistsWithId.first()
        }
    }
}