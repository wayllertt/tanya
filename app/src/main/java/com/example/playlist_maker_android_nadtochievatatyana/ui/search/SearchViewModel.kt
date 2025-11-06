package com.example.playlist_maker_android_nadtochievatatyana.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_nadtochievatatyana.domain.api.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.ViewModelProvider
import com.example.playlist_maker_android_nadtochievatatyana.creator.Creator
import kotlinx.coroutines.flow.update
import java.io.IOException


class SearchViewModel(
    private val tracksRepository: TracksRepository,
) : ViewModel() {

    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState: StateFlow<SearchState> = _searchScreenState.asStateFlow()

    fun search(whatSearch: String) {
        val query = whatSearch.trim()
        if (query.isEmpty()) {
            _searchScreenState.update { SearchState.Initial }
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchScreenState.update { SearchState.Searching }
                val tracks = tracksRepository.searchTracks(query)
                _searchScreenState.update { SearchState.Success(tracks) }
            } catch (error: IOException) {
                _searchScreenState.update { SearchState.Fail(error.message.orEmpty()) }
            }


        }
    }
    fun reset() {
        _searchScreenState.value = SearchState.Initial
    }

    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(Creator.getTracksRepository()) as T
                }
            }
    }
}