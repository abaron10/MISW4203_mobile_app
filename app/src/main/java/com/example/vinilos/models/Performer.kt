package com.example.vinilos.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONArray
import org.json.JSONObject

@Entity(tableName = "performers_table")
data class Performer (
    @PrimaryKey val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String? = null
) {
    companion object {
        fun fromJSONObject(jsonObject: JSONObject): Performer {
            val performer = Performer(
                id = jsonObject.getInt("id"),
                name = jsonObject.getString("name"),
                image = jsonObject.getString("image"),
                description = jsonObject.getString("description")
            )
            if(jsonObject.has("birthDate")) {
                jsonObject.getString("birthDate")
            }
            return performer
        }

        fun fromJSONArray(jsonArray: JSONArray): List<Performer> {
            val performersArray = mutableListOf<Performer>()
            for(i in 0 until jsonArray.length()) {
                performersArray.add(
                    Performer.fromJSONObject(jsonArray.getJSONObject(i))
                )
            }
            return performersArray
        }
    }
}