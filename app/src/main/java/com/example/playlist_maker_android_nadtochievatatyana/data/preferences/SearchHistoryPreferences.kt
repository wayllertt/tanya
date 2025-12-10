package com.example.playlist_maker_android_nadtochievatatyana.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SearchHistoryPreferences(
    private val dataStore: DataStore<Preferences>,
    private val coroutineScope: CoroutineScope = CoroutineScope(CoroutineName("search-history-preferences") + SupervisorJob())
) {
    fun addEntry(word: String) {
        if (word.isEmpty()) {
            return
        }

        coroutineScope.launch {
            dataStore.edit { preferences ->
                val historyString = preferences[PREFERENCES_KEY].orEmpty()
                val history = if (historyString.isNotEmpty()) {
                    historyString.split(SEPARATOR).toMutableList()
                } else {
                    mutableListOf()
                }

                history.remove(word)
                history.add(0, word)

                val updatedString = history
                    .take(MAX_ENTRIES)
                    .joinToString(SEPARATOR)

                preferences[PREFERENCES_KEY] = updatedString
            }
        }
    }

    suspend fun getEntries(): List<String> {
        val historyString = dataStore.data.first()[PREFERENCES_KEY].orEmpty()
        if (historyString.isEmpty()) return emptyList()
        return historyString.split(SEPARATOR)
    }

    companion object {
        private val PREFERENCES_KEY = preferencesKey<String>("search_history")
        private const val MAX_ENTRIES = 10
        private const val SEPARATOR = ","
    }
}