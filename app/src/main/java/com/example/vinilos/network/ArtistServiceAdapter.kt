package com.example.vinilos.network

import android.content.Context
import com.android.volley.Response
import com.android.volley.VolleyError
import com.example.vinilos.models.Album
import com.example.vinilos.models.Artist
import org.json.JSONArray

class ArtistServiceAdapter constructor(context: Context): NetworkServiceAdapter(context)  {
    companion object{
        var instance: ArtistServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: ArtistServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    fun getArtists(onComplete:(resp:List<Artist>)->Unit, onError: (error: VolleyError)->Unit){
        requestQueue.add(getRequest("musicians",  { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Artist>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Artist(artistId = item.getInt("id"),name = item.getString("name"), image = item.getString("image"), description = item.getString("description"), birthDate = item.getString("birthDate")))
                }
                onComplete(list)
            },
            {
                onError(it)
            }))
    }
}