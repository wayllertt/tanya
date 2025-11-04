package com.example.playlist_maker_android_nadtochievatatyana.creator

import com.example.playlist_maker_android_nadtochievatatyana.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android_nadtochievatatyana.data.repository.TracksRepositoryImpl
import com.example.playlist_maker_android_nadtochievatatyana.domain.api.TracksRepository

object Creator {
    fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(
            RetrofitNetworkClient(Storage()),
            )
    }
}
