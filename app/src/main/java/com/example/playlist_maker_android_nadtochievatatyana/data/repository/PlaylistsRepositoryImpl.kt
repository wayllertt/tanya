package com.example.playlist_maker_android_nadtochievatatyana.data.repository

import com.example.playlist_maker_android_nadtochievatatyana.data.storage.db.AppDatabase
import com.example.playlist_maker_android_nadtochievatatyana.data.storage.db.PlaylistEntity
import com.example.playlist_maker_android_nadtochievatatyana.data.storage.db.PlaylistWithTracks
import com.example.playlist_maker_android_nadtochievatatyana.data.storage.db.TrackEntity
import com.example.playlist_maker_android_nadtochievatatyana.domain.api.PlaylistsRepository
import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Playlist
import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    database: AppDatabase,
) : PlaylistsRepository {

    private val playlistDao = database.playlistDao()

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return playlistDao.getPlaylist(playlistId).map { entity ->
            entity?.toDomain()
        }
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return playlistDao.getAllPlaylists().map { playlists ->
            playlists.map { it.toDomain() }
        }
    }

    override suspend fun addNewPlaylist(name: String, description: String, coverImageUri: String?) {
        playlistDao.insertPlaylist(
            PlaylistEntity(
                name = name,
                description = description,
                coverImageUri = coverImageUri,
            ),
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        playlistDao.deletePlaylistById(id)
    }

}
private fun TrackEntity.toDomain(): Track =
    Track(
        id = id,
        trackName = trackName,
        artistName = artistName,
        trackTime = trackTime,
        artworkUrl100 = artworkUrl100,
        trackTimeMillis = trackTimeMillis,
        favorite = favorite,
    )

private fun PlaylistWithTracks.toDomain(): Playlist =
    Playlist(
        id = playlist.id,
        name = playlist.name,
        description = playlist.description,
        coverImageUri = playlist.coverImageUri,
        tracks = tracks.map { it.toDomain() },
    )