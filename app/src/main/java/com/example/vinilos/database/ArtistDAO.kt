package com.example.vinilos.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vinilos.models.Album
import com.example.vinilos.models.Artist

@Dao
interface ArtistDAO {
    @Query("SELECT * FROM artists_table ORDER BY createdAt ASC")
    fun getArtists(): List<Artist>

    @Query("SELECT * FROM artists_table WHERE artistId = :id")
    fun getArtist(id: Int): List<Artist>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(artist: Artist)

    @Query("DELETE FROM artists_table")
    suspend fun deleteAll(): Int
}