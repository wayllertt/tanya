package com.example.playlist_maker_android_nadtochievatatyana.domain.api

import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Track

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
}