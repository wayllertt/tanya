package com.example.playlist_maker_android_nadtochievatatyana.data.storage.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {
    @Query("SELECT * FROM tracks WHERE trackName = :name AND artistName = :artist LIMIT 1")
    suspend fun getTrackByNameAndArtist(name: String, artist: String): TrackEntity?

    @Query("SELECT * FROM tracks WHERE id = :id LIMIT 1")
    suspend fun getTrackById(id: Long): TrackEntity?

    @Query("SELECT * FROM tracks WHERE favorite = 1")
    fun getFavoriteTracks(): Flow<List<TrackEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrack(track: TrackEntity)

    @Query("UPDATE tracks SET playlistId = :playlistId WHERE id = :id")
    suspend fun updateTrackPlaylist(id: Long, playlistId: Long)

    @Query("UPDATE tracks SET favorite = :isFavorite WHERE id = :id")
    suspend fun updateTrackFavorite(id: Long, isFavorite: Boolean)

    @Query("UPDATE tracks SET playlistId = 0 WHERE playlistId = :playlistId")
    suspend fun clearPlaylistTracks(playlistId: Long)
}