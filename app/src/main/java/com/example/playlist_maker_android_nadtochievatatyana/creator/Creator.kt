package com.example.playlist_maker_android_nadtochievatatyana.creator

import android.content.Context
import com.example.playlist_maker_android_nadtochievatatyana.data.network.RetrofitNetworkClient
import com.example.playlist_maker_android_nadtochievatatyana.data.preferences.PreferencesProvider
import com.example.playlist_maker_android_nadtochievatatyana.data.preferences.SearchHistoryPreferences
import com.example.playlist_maker_android_nadtochievatatyana.data.repository.PlaylistsRepositoryImpl
import com.example.playlist_maker_android_nadtochievatatyana.data.repository.SearchHistoryRepositoryImpl
import com.example.playlist_maker_android_nadtochievatatyana.data.repository.TracksRepositoryImpl
import com.example.playlist_maker_android_nadtochievatatyana.data.storage.DatabaseProvider
import com.example.playlist_maker_android_nadtochievatatyana.domain.api.PlaylistsRepository
import com.example.playlist_maker_android_nadtochievatatyana.domain.api.SearchHistoryRepository
import com.example.playlist_maker_android_nadtochievatatyana.domain.api.TracksRepository

object Creator {
    fun getTracksRepository(context: Context): TracksRepository {
        val database = DatabaseProvider.getDatabase(context)
        return TracksRepositoryImpl(
            networkClient = RetrofitNetworkClient(),
            database = database,
        )
    }

    fun getPlaylistsRepository(context: Context): PlaylistsRepository {
        val database = DatabaseProvider.getDatabase(context)
        return PlaylistsRepositoryImpl(
            database = database,
        )
    }

    fun getSearchHistoryRepository(context: Context): SearchHistoryRepository {
        val dataStore = PreferencesProvider.getSearchHistoryDataStore(context)
        val preferences = SearchHistoryPreferences(dataStore)
        return SearchHistoryRepositoryImpl(preferences)
    }
}