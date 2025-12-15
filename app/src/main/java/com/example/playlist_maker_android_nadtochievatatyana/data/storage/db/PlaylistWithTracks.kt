package com.example.playlist_maker_android_nadtochievatatyana.data.storage.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PlaylistWithTracks(
    @Embedded
    val playlist: PlaylistEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(PlaylistTrackCrossRef::class),
    )
    val tracks: List<TrackEntity>,
)