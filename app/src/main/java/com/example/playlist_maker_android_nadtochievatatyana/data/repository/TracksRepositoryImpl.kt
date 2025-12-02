package com.example.playlist_maker_android_nadtochievatatyana.data.repository

import com.example.playlist_maker_android_nadtochievatatyana.data.dto.TracksSearchRequest
import com.example.playlist_maker_android_nadtochievatatyana.data.dto.TracksSearchResponse
import com.example.playlist_maker_android_nadtochievatatyana.data.storage.DatabaseMock
import com.example.playlist_maker_android_nadtochievatatyana.domain.api.NetworkClient
import com.example.playlist_maker_android_nadtochievatatyana.domain.api.TracksRepository
import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Track
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlin.math.abs

class ResponseCodeException(val code: Int) : Exception()

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    private val database: DatabaseMock,
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        if (response.resultCode != 200) {
            throw ResponseCodeException(response.resultCode)
        }
        return (response as TracksSearchResponse).results.map { dto ->
            val trackTimeMillis = dto.trackTimeMillis ?: 0L
            val seconds = trackTimeMillis / 1000
            val minutes = seconds / 60
            val trackTime = "%02d".format(minutes) + ":" + "%02d".format(seconds - minutes * 60)
            val id = dto.trackId ?: abs((dto.trackName.orEmpty() + dto.artistName.orEmpty() + trackTimeMillis).hashCode()).toLong()
            Track(
                id = id,
                trackName = dto.trackName.orEmpty(),
                artistName = dto.artistName.orEmpty(),
                trackTime = trackTime,
                artworkUrl100 = dto.artworkUrl100,
                trackTimeMillis = dto.trackTimeMillis,
            )
        }
    }

    override suspend fun getTrackByNameAndArtist(track: Track): Track? {
        return database.getTrackByNameAndArtist(track)
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return database.getFavoriteTracks()
    }

    override suspend fun insertSongToPlaylist(track: Track, playlistId: Long) {
        database.insertSongToPlaylist(mergeWithStoredTrack(track), playlistId)
    }

    override suspend fun deleteSongFromPlaylist(track: Track) {
        database.deleteSongFromPlaylist(mergeWithStoredTrack(track))
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        database.updateTrackFavoriteStatus(mergeWithStoredTrack(track), isFavorite)
    }

    override suspend fun deleteTracksByPlaylistId(playlistId: Long) {
        database.deleteTracksByPlaylistId(playlistId)
    }

    private fun mergeWithStoredTrack(track: Track): Track {
        val storedTrack = database.getTrackById(track.id)
        return storedTrack?.copy(
            trackName = track.trackName,
            artistName = track.artistName,
            trackTime = track.trackTime,
            artworkUrl100 = track.artworkUrl100,
            trackTimeMillis = track.trackTimeMillis,
        ) ?: track
    }
}