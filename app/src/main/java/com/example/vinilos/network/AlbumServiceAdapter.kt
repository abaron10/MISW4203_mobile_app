package com.example.vinilos.network

import android.content.Context
import com.android.volley.VolleyError
import com.example.vinilos.models.Album
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AlbumServiceAdapter constructor(context: Context): NetworkServiceAdapter(context)  {
    companion object{
        var instance: AlbumServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: AlbumServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    suspend fun getAlbums() = suspendCoroutine<List<Album>>{ cont ->
        requestQueue.add(
            getRequest("Albums",  { response ->
                val resp = JSONArray(response)
                val list = Album.fromJSONArray(resp)
                cont.resume(list)
            }, {
                cont.resumeWithException(it)
            })
        )
    }

    suspend fun getAlbum(albumId: Int) = suspendCoroutine<Album> { cont ->
        requestQueue.add(
            getRequest("albums/$albumId",  { response ->
                val resp = JSONObject(response)
                val album = Album.fromJSONObject(resp)
                cont.resume(album)
            }, {
                cont.resumeWithException(it)
            })
        )
    }

    fun createAlbum(albumParams: Map<String, String>,
                    onComplete: (resp: Album) -> Unit,
                    onError: (error: VolleyError) -> Unit){

        requestQueue.add(postRequest("albums", JSONObject(albumParams), { response ->
            val createdAlbum = Album(
                albumId = response.getInt("id"),
                name = response.getString("name"),
                cover = response.getString("cover"),
                recordLabel = response.getString("recordLabel"),
                releaseDate = response.getString("releaseDate"),
                genre = response.getString("genre"),
                description = response.getString("description")
            )

            onComplete(createdAlbum)
        },
            {
                onError(it)
            }))
    }
}