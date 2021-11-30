package com.example.vinilos.database
import androidx.room.Dao
import androidx.room.Query

@Dao
interface TrackDAO {
    @Query("DELETE FROM tracks_table")
    suspend fun deleteAll(): Int
}