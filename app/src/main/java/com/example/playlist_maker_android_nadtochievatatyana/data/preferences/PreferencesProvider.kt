package com.example.playlist_maker_android_nadtochievatatyana.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

object PreferencesProvider {
    private val Context.searchHistoryPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "search_history")

    fun getSearchHistoryDataStore(context: Context): DataStore<Preferences> {
        return context.searchHistoryPreferencesDataStore
    }
}