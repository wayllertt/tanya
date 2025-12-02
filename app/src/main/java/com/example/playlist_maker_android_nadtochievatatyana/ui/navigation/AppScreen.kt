package com.example.playlist_maker_android_nadtochievatatyana.ui.navigation

private const val TRACK_DETAILS_BASE_ROUTE = "track_details"

sealed class AppScreen(val route: String) {
    data object Main : AppScreen("main")
    data object Search : AppScreen("search")
    data object Settings : AppScreen("settings")
    data object Favorites : AppScreen("favorites")
    data object Playlists : AppScreen("playlists")
    data object CreatePlaylist : AppScreen("create_playlist")
    data object Playlist : AppScreen("playlist/{playlistId}") {
        const val baseRoute: String = "playlist"
    }
    data object TrackDetails : AppScreen("$TRACK_DETAILS_BASE_ROUTE/{trackId}/{trackName}/{artistName}/{trackTime}") {
        const val baseRoute: String = TRACK_DETAILS_BASE_ROUTE
    }

    companion object
}
