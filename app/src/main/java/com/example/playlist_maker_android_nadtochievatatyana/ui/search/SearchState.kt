package com.example.playlist_maker_android_nadtochievatatyana.ui.search

import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Track

sealed interface SearchState {
    data object Initial : SearchState
    data object Searching : SearchState
    data class Success(val tracks: List<Track>) : SearchState
    data class Fail(val reason: SearchError) : SearchState
}

enum class SearchError {
    NETWORK,
    EMPTY_RESULT,
}