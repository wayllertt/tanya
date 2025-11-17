package com.example.playlist_maker_android_nadtochievatatyana.domain.models

import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Track

data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String,
    val tracks: List<Track> = emptyList(),
)