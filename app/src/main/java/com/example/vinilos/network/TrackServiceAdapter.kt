package com.example.vinilos.network

import android.content.Context
import com.example.vinilos.models.Track
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TrackServiceAdapter constructor(context: Context): NetworkServiceAdapter(context)  {
    companion object{
        var instance: TrackServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: TrackServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    suspend fun createTrack(trackParams: Map<String, String>, albumId: Int) = suspendCoroutine<Track> { cont ->
        requestQueue.add(
            postRequest("albums/$albumId/tracks", JSONObject(trackParams), { response ->
                val createdTrack = Track(
                    id = response.getInt("id"),
                    name = response.getString("name"),
                    duration = response.getString("duration")
                )
                cont.resume(createdTrack)
            }, {
                cont.resumeWithException(it)
            })
        )
    }

}