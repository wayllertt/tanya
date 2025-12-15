package com.example.playlist_maker_android_nadtochievatatyana.data.storage.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import androidx.room.OnConflictStrategy

@Dao
interface PlaylistDao {
    @Transaction
    @Query("SELECT * FROM playlists WHERE id = :playlistId")
    fun getPlaylist(playlistId: Long): Flow<PlaylistWithTracks?>

    @Transaction
    @Query("SELECT * FROM playlists")
    fun getAllPlaylists(): Flow<List<PlaylistWithTracks>>

    @Insert
    suspend fun insertPlaylist(playlistEntity: PlaylistEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTrackToPlaylist(crossRef: PlaylistTrackCrossRef)

    @Query("DELETE FROM playlist_tracks WHERE playlistId = :playlistId AND trackId = :trackId")
    suspend fun deleteTrackFromPlaylist(playlistId: Long, trackId: Long)

    @Query("DELETE FROM playlist_tracks WHERE playlistId = :playlistId")
    suspend fun deleteTracksByPlaylist(playlistId: Long)


    @Query("DELETE FROM playlists WHERE id = :id")
    suspend fun deletePlaylistById(id: Long)
}