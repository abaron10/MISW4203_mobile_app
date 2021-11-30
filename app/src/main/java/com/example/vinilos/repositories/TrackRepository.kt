package com.example.vinilos.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilos.database.VinylRoomDatabase
import com.example.vinilos.network.TrackServiceAdapter

class TrackRepository (val application: Application){

    suspend fun addTrack(trackParams: Map<String, String>, albumId: Int) {
        TrackServiceAdapter.getInstance(application).createTrack(trackParams, albumId)
        val db = VinylRoomDatabase.getDatabase(application.applicationContext)
        db.trackDao().deleteAll()
        db.albumDao().deleteAlbum(albumId)
    }
}