package com.example.vinilos.database

import androidx.room.TypeConverter
import com.example.vinilos.models.Performer
import com.example.vinilos.models.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class DataConverter {
    @TypeConverter
    fun fromPerformerList(value: List<Performer>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Performer>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toPerformerList(value: String): List<Performer> {
        val gson = Gson()
        val type = object : TypeToken<List<Performer>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromTracksList(value: List<Track>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Track>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toTracksList(value: String): List<Track> {
        val gson = Gson()
        val type = object : TypeToken<List<Track>>() {}.type
        return gson.fromJson(value, type)
    }
}