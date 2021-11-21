package com.example.vinilos.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "artists_table")
data class Artist (
    @PrimaryKey val artistId: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String,
    )
