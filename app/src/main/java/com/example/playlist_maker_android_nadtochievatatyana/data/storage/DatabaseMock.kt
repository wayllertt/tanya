package com.example.playlist_maker_android_nadtochievatatyana.data.storage

import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Playlist
import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class DatabaseMock {
    private val playlists = mutableListOf<Playlist>()
    private val tracks = mutableListOf<Track>()

    private val playlistsFlow = MutableStateFlow<List<Playlist>>(emptyList())
    private val favoriteTracksFlow = MutableStateFlow<List<Track>>(emptyList())

    fun getAllPlaylists(): Flow<List<Playlist>> = playlistsFlow

    fun getPlaylist(playlistId: Long): Flow<Playlist?> = playlistsFlow.map { list ->
        list.firstOrNull { it.id == playlistId }
    }

    fun insertPlaylist(playlist: Playlist) {
        val nextId = if (playlists.isEmpty()) 1L else playlists.maxOf { it.id } + 1
        playlists.add(playlist.copy(id = nextId, tracks = emptyList()))
        notifyPlaylistsChanged()
    }

    fun deletePlaylistById(id: Long) {
        playlists.removeAll { it.id == id }
        deleteTracksByPlaylistId(id)
        notifyPlaylistsChanged()
    }

    fun getTrackByNameAndArtist(track: Track): Track? =
        tracks.find { it.trackName == track.trackName && it.artistName == track.artistName }

    fun insertTrack(track: Track) {
        tracks.removeAll { it.id == track.id }
        tracks.add(track)
        notifyFavoritesChanged()
        notifyPlaylistsChanged()
    }

    fun getFavoriteTracks(): Flow<List<Track>> = favoriteTracksFlow

    fun insertSongToPlaylist(track: Track, playlistId: Long) {
        insertTrack(track.copy(playlistId = playlistId))
    }

    fun deleteSongFromPlaylist(track: Track) {
        insertTrack(track.copy(playlistId = 0))
    }

    fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        insertTrack(track.copy(favorite = isFavorite))
    }

    fun deleteTracksByPlaylistId(playlistId: Long) {
        val updatedTracks = tracks.map { track ->
            if (track.playlistId == playlistId) {
                track.copy(playlistId = 0)
            } else {
                track
            }
        }
        tracks.clear()
        tracks.addAll(updatedTracks)
        notifyPlaylistsChanged()
        notifyFavoritesChanged()
    }

    fun getTrackById(id: Long): Track? = tracks.firstOrNull { it.id == id }

    private fun notifyPlaylistsChanged() {
        val filledPlaylists = playlists.map { playlist ->
            playlist.copy(tracks = tracks.filter { it.playlistId == playlist.id })
        }
        playlistsFlow.value = filledPlaylists
    }

    private fun notifyFavoritesChanged() {
        favoriteTracksFlow.value = tracks.filter { it.favorite }
    }
}