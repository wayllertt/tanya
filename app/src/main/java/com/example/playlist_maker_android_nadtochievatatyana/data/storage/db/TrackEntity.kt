package com.example.playlist_maker_android_nadtochievatatyana.data.storage.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tracks",
    indices = [Index(value = ["trackName", "artistName"])]
)
data class TrackEntity(
    @PrimaryKey
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl100: String?,
    val trackTimeMillis: Long?,
    val favorite: Boolean = false,
)