package com.example.playlist_maker_android_nadtochievatatyana.data.storage.db

import androidx.room.Embedded
import androidx.room.Relation

data class PlaylistWithTracks(
    @Embedded
    val playlist: PlaylistEntity,
    @Relation(parentColumn = "id", entityColumn = "playlistId")
    val tracks: List<TrackEntity>,
)