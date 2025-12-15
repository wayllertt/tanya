package com.example.playlist_maker_android_nadtochievatatyana.data.repository

import com.example.playlist_maker_android_nadtochievatatyana.data.dto.TracksSearchRequest
import com.example.playlist_maker_android_nadtochievatatyana.data.dto.TracksSearchResponse
import com.example.playlist_maker_android_nadtochievatatyana.data.storage.db.AppDatabase
import com.example.playlist_maker_android_nadtochievatatyana.data.storage.db.TrackEntity
import com.example.playlist_maker_android_nadtochievatatyana.domain.api.NetworkClient
import com.example.playlist_maker_android_nadtochievatatyana.domain.api.TracksRepository
import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.math.abs
import com.example.playlist_maker_android_nadtochievatatyana.data.storage.db.PlaylistTrackCrossRef

class ResponseCodeException(val code: Int) : Exception()

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    database: AppDatabase,
) : TracksRepository {

    private val trackDao = database.trackDao()
    private val playlistDao = database.playlistDao()

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
        return trackDao.getTrackByNameAndArtist(track.trackName, track.artistName)?.toDomain()
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return trackDao.getFavoriteTracks().map { tracks -> tracks.map { it.toDomain() } }
    }

    override suspend fun insertSongToPlaylist(track: Track, playlistId: Long) {
        val mergedTrack = mergeWithStoredTrack(track)
        trackDao.insertTrack(mergedTrack.toEntity())
        playlistDao.insertTrackToPlaylist(PlaylistTrackCrossRef(playlistId, track.id))
    }

    override suspend fun deleteSongFromPlaylist(track: Track, playlistId: Long) {
        playlistDao.deleteTrackFromPlaylist(playlistId = playlistId, trackId = track.id)
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        val mergedTrack = mergeWithStoredTrack(track).copy(favorite = isFavorite)
        trackDao.insertTrack(mergedTrack.toEntity())
    }

    override suspend fun deleteTracksByPlaylistId(playlistId: Long) {
        playlistDao.deleteTracksByPlaylist(playlistId)
    }

    private suspend fun mergeWithStoredTrack(track: Track): Track {
        val storedTrack = trackDao.getTrackById(track.id)?.toDomain()
        return storedTrack?.copy(
            trackName = track.trackName,
            artistName = track.artistName,
            trackTime = track.trackTime,
            artworkUrl100 = track.artworkUrl100 ?: storedTrack.artworkUrl100,
            trackTimeMillis = track.trackTimeMillis ?: storedTrack.trackTimeMillis,
        ) ?: track
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

private fun Track.toEntity(): TrackEntity =
    TrackEntity(
        id = id,
        trackName = trackName,
        artistName = artistName,
        trackTime = trackTime,
        artworkUrl100 = artworkUrl100,
        trackTimeMillis = trackTimeMillis,
        favorite = favorite,
    )