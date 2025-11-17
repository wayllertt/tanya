package com.example.playlist_maker_android_nadtochievatatyana.data.repository

import com.example.playlist_maker_android_nadtochievatatyana.data.storage.DatabaseMock
import com.example.playlist_maker_android_nadtochievatatyana.domain.api.PlaylistsRepository
import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Playlist
import kotlinx.coroutines.flow.Flow

class PlaylistsRepositoryImpl(
    private val database: DatabaseMock,
) : PlaylistsRepository {

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return database.getPlaylist(playlistId)
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return database.getAllPlaylists()
    }

    override suspend fun addNewPlaylist(name: String, description: String) {
        database.insertPlaylist(
            Playlist(
                name = name,
                description = description,
            ),
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        database.deletePlaylistById(id)
    }
}