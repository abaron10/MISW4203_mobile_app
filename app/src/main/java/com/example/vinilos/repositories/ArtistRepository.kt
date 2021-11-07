package com.example.vinilos.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilos.models.Artist
import com.example.vinilos.network.ArtistServiceAdapter

class ArtistRepository (val application: Application){
    fun refreshData(callback: (List<Artist>)->Unit, onError: (VolleyError)->Unit) {
        ArtistServiceAdapter.getInstance(application).getArtists({
            callback(it)
        }, onError)
    }
}