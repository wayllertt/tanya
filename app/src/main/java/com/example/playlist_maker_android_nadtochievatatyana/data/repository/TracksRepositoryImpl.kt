package com.example.playlist_maker_android_nadtochievatatyana.data.repository

import com.example.playlist_maker_android_nadtochievatatyana.data.dto.TracksSearchRequest
import com.example.playlist_maker_android_nadtochievatatyana.data.dto.TracksSearchResponse
import com.example.playlist_maker_android_nadtochievatatyana.domain.api.NetworkClient
import com.example.playlist_maker_android_nadtochievatatyana.domain.api.TracksRepository
import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Track
import kotlinx.coroutines.delay

class TracksRepositoryImpl(private val networkClient: NetworkClient) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        delay(1000)
        return if (response.resultCode == 200) {
            (response as TracksSearchResponse).results.map {
                val seconds = it.trackTimeMillis / 1000
                val minutes = seconds / 60
                val trackTime = "%02d".format(minutes) + ":" + "%02d".format(seconds - minutes * 60)
                Track(it.trackName, it.artistName, trackTime)
            }
        } else {
            emptyList()
        }
    }
}