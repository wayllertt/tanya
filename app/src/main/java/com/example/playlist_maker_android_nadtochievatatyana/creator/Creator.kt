package com.example.playlist_maker_android_nadtochievatatyana.creator

import android.content.Context
import com.example.playlist_maker_android_nadtochievatatyana.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android_nadtochievatatyana.data.repository.PlaylistsRepositoryImpl
import com.example.playlist_maker_android_nadtochievatatyana.data.repository.TracksRepositoryImpl
import com.example.playlist_maker_android_nadtochievatatyana.data.storage.DatabaseProvider
import com.example.playlist_maker_android_nadtochievatatyana.domain.api.PlaylistsRepository
import com.example.playlist_maker_android_nadtochievatatyana.domain.api.TracksRepository

object Creator {
    fun getTracksRepository(context: Context): TracksRepository {
        return TracksRepositoryImpl(
            networkClient = RetrofitNetworkClient(Storage(context.resources)),
            database = DatabaseProvider.database,
        )
    }

    fun getPlaylistsRepository(): PlaylistsRepository {
        return PlaylistsRepositoryImpl(
            database = DatabaseProvider.database,
        )
    }
}