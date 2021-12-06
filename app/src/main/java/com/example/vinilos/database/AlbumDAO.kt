package com.example.vinilos.database
import androidx.room.*
import com.example.vinilos.models.Album

@Dao
interface AlbumDAO {
    @Query("SELECT * FROM albums_table ORDER BY createdAt ASC")
    fun getAlbums(): List<Album>

    @Query("SELECT * FROM albums_table WHERE albumId = :id")
    fun getAlbum(id: Int): List<Album>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(album: Album)

    @Query("DELETE FROM albums_table")
    suspend fun deleteAll(): Int

    @Query("DELETE FROM albums_table WHERE albumId = :id")
    suspend fun deleteAlbum(id: Int): Int
}