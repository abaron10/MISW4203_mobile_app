package com.example.vinilos.database

import android.content.Context
import androidx.room.*
import com.example.vinilos.models.*

@Database(
    entities = [Album::class, Collector::class, Artist::class, Performer::class, Track::class],
    version = 1, exportSchema = false
)
@TypeConverters(DataConverter::class)
abstract class VinylRoomDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDAO
    abstract fun collectorDao(): CollectorDAO
    abstract fun artistDao(): ArtistDAO
    abstract fun performerDao(): PerformerDAO
    abstract fun trackDao(): TrackDAO

    companion object {
        @Volatile
        private var INSTANCE: VinylRoomDatabase? = null

        fun getDatabase(context: Context): VinylRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VinylRoomDatabase::class.java,
                    "vinyls_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}