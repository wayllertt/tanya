package com.example.playlist_maker_android_nadtochievatatyana.domain.api

import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>

    suspend fun getTrackByNameAndArtist(track: Track): Track?

    fun getFavoriteTracks(): Flow<List<Track>>

    suspend fun insertSongToPlaylist(track: Track, playlistId: Long)

    suspend fun deleteSongFromPlaylist(track: Track)

    suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean)

    suspend fun deleteTracksByPlaylistId(playlistId: Long)
}