package com.example.vinilos.models

import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

data class Album (
    val albumId: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
    val performers: List<Performer> = mutableListOf<Performer>(),
    val tracks: List<Track> = mutableListOf<Track>()
) {
    companion object {
        fun fromJSONObject(jsonObject: JSONObject): Album {
            return Album(
                albumId = jsonObject.getInt("id"),
                name = jsonObject.getString("name"),
                cover = jsonObject.getString("cover"),
                recordLabel = jsonObject.getString("recordLabel"),
                releaseDate = jsonObject.getString("releaseDate"),
                genre = jsonObject.getString("genre"),
                description = jsonObject.getString("description"),
                performers = Performer.fromJSONArray(
                    jsonObject.getJSONArray("performers")
                ),
                tracks = Track.fromJSONArray(
                    jsonObject.getJSONArray("tracks")
                )
            )
        }

        fun fromJSONArray(jsonArray: JSONArray): List<Album> {
            val albumsArray = mutableListOf<Album>()
            for(i in 0 until jsonArray.length()) {
                albumsArray.add(
                    Album.fromJSONObject(jsonArray.getJSONObject(i))
                )
            }
            return albumsArray
        }
    }

    fun formattedReleaseDate(): String {
        val newFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return newFormat.format(this.releaseDateToDate())
    }

    fun performersToString(): String {
        return performers.joinToString {
            it.name
        }
    }

    private fun releaseDateToDate() : Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        return formatter.parse(releaseDate)
    }
}