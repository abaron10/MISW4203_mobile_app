package com.example.vinilos.network

import android.content.Context
import com.example.vinilos.models.Album
import com.example.vinilos.models.Artist
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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

    suspend fun getArtists() = suspendCoroutine<List<Artist>>{ cont ->
        requestQueue.add(
            getRequest("musicians",  { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Artist>()
                var item: JSONObject
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    list.add(i, Artist(artistId = item.getInt("id"),name = item.getString("name"), image = item.getString("image"), description = item.getString("description"), birthDate = item.getString("birthDate"), createdAt = i.toLong()))
                }
                cont.resume(list)
            }, {
                cont.resumeWithException(it)
            })
        )
    }

    suspend fun getArtist(artistId: Int) = suspendCoroutine<Artist> { cont ->
        requestQueue.add(
            getRequest("musicians/$artistId",  { response ->
                val item = JSONObject(response)
                val artist = Artist(artistId = item.getInt("id"),name = item.getString("name"), image = item.getString("image"), description = item.getString("description"), birthDate = item.getString("birthDate"))
                cont.resume(artist)
            }, {
                cont.resumeWithException(it)
            })
        )
    }
}