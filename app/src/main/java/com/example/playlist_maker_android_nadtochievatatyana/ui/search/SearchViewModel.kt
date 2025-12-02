package com.example.playlist_maker_android_nadtochievatatyana.ui.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker_android_nadtochievatatyana.domain.api.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.playlist_maker_android_nadtochievatatyana.R
import com.example.playlist_maker_android_nadtochievatatyana.creator.Creator
import kotlinx.coroutines.withContext
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import com.example.playlist_maker_android_nadtochievatatyana.data.repository.ResponseCodeException

class SearchViewModel(
    private val tracksRepository: TracksRepository,
) : ViewModel() {

    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState: StateFlow<SearchState> = _searchScreenState.asStateFlow()
    private var lastQuery: String? = null

    fun search(whatSearch: String) {
        val query = whatSearch.trim()
        if (query.isEmpty()) {
            _searchScreenState.update { SearchState.Initial }
            lastQuery = null
            return
        }
        lastQuery = query
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _searchScreenState.update { SearchState.Searching }
                val tracks = tracksRepository.searchTracks(query)
                _searchScreenState.update { SearchState.Success(tracks) }
            } catch (error: IOException) {
                _searchScreenState.update { SearchState.Fail(R.string.search_error_network) }
            } catch (_: ResponseCodeException) {
                _searchScreenState.update { SearchState.Fail(R.string.search_error_server) }
            } catch (_: Exception) {
                _searchScreenState.update { SearchState.Fail(R.string.search_error_unknown) }
            }
        }
    }
    fun reset() {
        _searchScreenState.value = SearchState.Initial
        lastQuery = null
    }

    fun repeatLastSearch() {
        val query = lastQuery ?: return
        search(query)
    }

    companion object {
        fun getViewModelFactory(context: Context): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    val appContext = context.applicationContext
                    return SearchViewModel(Creator.getTracksRepository(appContext)) as T
                }
            }
    }
}