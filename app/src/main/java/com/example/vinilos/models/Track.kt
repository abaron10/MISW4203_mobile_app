package com.example.vinilos.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONArray
import org.json.JSONObject

@Entity(tableName = "tracks_table")
data class Track (
    @PrimaryKey val id: Int,
    val name: String,
    val duration: String
) {
    companion object {
        fun fromJSONObject(jsonObject: JSONObject): Track {
            return Track(
                id = jsonObject.getInt("id"),
                name = jsonObject.getString("name"),
                duration = jsonObject.getString("duration")
            )
        }

        fun fromJSONArray(jsonArray: JSONArray): List<Track> {
            val tracksArray = mutableListOf<Track>()
            for(i in 0 until jsonArray.length()) {
                tracksArray.add(
                    Track.fromJSONObject(jsonArray.getJSONObject(i))
                )
            }
            return tracksArray
        }
    }
}