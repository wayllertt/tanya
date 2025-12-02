package com.example.playlist_maker_android_nadtochievatatyana.domain.models

data class Track(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl100: String? = null,
    val trackTimeMillis: Long? = null,
    val playlistId: Long = 0,
    val favorite: Boolean = false,
)