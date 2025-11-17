package com.example.playlist_maker_android_nadtochievatatyana.ui.navigation

sealed class AppScreen(val route: String) {
    data object Main : AppScreen("main")
    data object Search : AppScreen("search")
    data object Settings : AppScreen("settings")
    data object Favorites : AppScreen("favorites")
    data object Playlists : AppScreen("playlists")
    data object CreatePlaylist : AppScreen("create_playlist")
    data object TrackDetails : AppScreen("$baseRoute/{trackId}/{trackName}/{artistName}/{trackTime}") {
        const val baseRoute: String = "track_details"
    }

    companion object
}