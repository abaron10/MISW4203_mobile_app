package com.example.vinilos.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vinilos.models.Artist
import com.example.vinilos.models.Collector

@Dao
interface CollectorDAO {
    @Query("SELECT * FROM collectors_table ORDER BY createdAt ASC")
    fun getCollectors(): List<Collector>

    @Query("SELECT * FROM collectors_table WHERE collectorId = :id")
    fun getCollector(id: Int): List<Collector>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(collector: Collector)

    @Query("DELETE FROM collectors_table")
    suspend fun deleteAll(): Int
}