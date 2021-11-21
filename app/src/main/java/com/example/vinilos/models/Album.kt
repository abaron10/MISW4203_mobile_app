package com.example.vinilos.models

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "albums_table")
data class Album (
    @PrimaryKey val albumId: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
    val performers: List<Performer> = mutableListOf<Performer>(),
    val tracks: List<Track> = mutableListOf<Track>(),
    var createdAt: Long = System.currentTimeMillis()
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
            var album: Album? = null
            for(i in 0 until jsonArray.length()) {
                album = Album.fromJSONObject(jsonArray.getJSONObject(i))
                album.createdAt = i.toLong()
                albumsArray.add(
                    album
                )
            }
            return albumsArray
        }
    }

    fun formattedReleaseDate(): String {
        val newFormat = SimpleDateFormat("dd/MM/yyyy")
        return newFormat.format(this.releaseDateToDate())
    }

    fun performersToString(): String {
        return performers.joinToString {
            it.name
        }
    }

    private fun releaseDateToDate() : Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        return formatter.parse(releaseDate)
    }
}