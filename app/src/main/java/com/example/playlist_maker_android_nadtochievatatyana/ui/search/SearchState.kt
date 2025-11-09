package com.example.playlist_maker_android_nadtochievatatyana.ui.search

import androidx.annotation.StringRes
import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Track

sealed class SearchState {
    data object Initial : SearchState()
    data object Searching : SearchState()
    data class Success(val tracks: List<Track>) : SearchState()
    data class Fail(@StringRes val messageResId: Int) : SearchState()
}