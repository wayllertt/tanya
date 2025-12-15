package com.example.playlist_maker_android_nadtochievatatyana.data.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PlaylistEntity::class, TrackEntity::class, PlaylistTrackCrossRef::class],
    version = 3,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao
    abstract fun trackDao(): TrackDao
}