package com.example.playlist_maker_android_nadtochievatatyana.ui.navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Track
import com.example.playlist_maker_android_nadtochievatatyana.ui.screen.CreatePlaylistScreen
import com.example.playlist_maker_android_nadtochievatatyana.ui.screen.FavoritesScreen
import com.example.playlist_maker_android_nadtochievatatyana.ui.screen.MainScreen
import com.example.playlist_maker_android_nadtochievatatyana.ui.screen.PlaylistsScreen
import com.example.playlist_maker_android_nadtochievatatyana.ui.screen.SettingsScreen
import com.example.playlist_maker_android_nadtochievatatyana.ui.screen.TrackDetailsRoute
import com.example.playlist_maker_android_nadtochievatatyana.ui.search.SearchRoute
import com.example.playlist_maker_android_nadtochievatatyana.ui.screen.PlaylistRoute

@Composable
fun PlaylistHost(navController: NavHostController) {
    val navigateUp: () -> Unit = { navController.navigateUp() }

    NavHost(
        navController = navController,
        startDestination = AppScreen.Main.route,
    ) {
        composable(AppScreen.Main.route) {
            MainScreen(
                onOpenSearch = { navController.navigate(AppScreen.Search.route) },
                onOpenPlaylists = { navController.navigate(route = AppScreen.Playlists.route) },
                onOpenFavorites = { navController.navigate(route = AppScreen.Favorites.route) },
                onOpenSettings = { navController.navigate(AppScreen.Settings.route) },
            )
        }
        composable(AppScreen.Search.route) {
            SearchRoute(
                modifier = Modifier.fillMaxSize(),
                onBack = navigateUp,
                onTrackClick = { track ->
                    navController.navigate(AppScreen.trackDetailsRoute(track))
                },
            )
        }
        composable(AppScreen.Playlists.route) {
            PlaylistsScreen(
                onBack = navigateUp,
                onCreatePlaylist = { navController.navigate(AppScreen.CreatePlaylist.route) },
                onPlaylistClick = { playlist ->
                    navController.navigate(AppScreen.playlistRoute(playlist.id))
                },
            )
        }
        composable(AppScreen.CreatePlaylist.route) {
            CreatePlaylistScreen(
                onBack = navigateUp,
            )
        }
        composable(
            route = AppScreen.Playlist.route,
            arguments = listOf(
                navArgument("playlistId") { type = NavType.LongType },
            ),
        ) { backStackEntry ->
            val playlistId = backStackEntry.arguments?.getLong("playlistId") ?: 0L
            PlaylistRoute(
                playlistId = playlistId,
                onBack = navigateUp,
                onTrackClick = { track ->
                    navController.navigate(AppScreen.trackDetailsRoute(track))
                },
            )
        }
        composable(AppScreen.Favorites.route) {
            FavoritesScreen(
                onBack = navigateUp,
                onTrackClick = { track ->
                    navController.navigate(AppScreen.trackDetailsRoute(track))
                },
            )
        }
        composable(AppScreen.Settings.route) {
            SettingsScreen(
                onBack = navigateUp,
            )
        }
        composable(
            route = AppScreen.TrackDetails.route,
            arguments = listOf(
                navArgument("trackId") { type = NavType.LongType },
                navArgument("trackName") { type = NavType.StringType },
                navArgument("artistName") { type = NavType.StringType },
                navArgument("trackTime") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val arguments = backStackEntry.arguments ?: return@composable
            val track = Track(
                id = arguments.getLong("trackId"),
                trackName = arguments.getString("trackName") ?: "",
                artistName = arguments.getString("artistName") ?: "",
                trackTime = arguments.getString("trackTime") ?: "",
            )
            TrackDetailsRoute(
                track = track,
                onBack = navigateUp,
            )
        }
    }
}

fun AppScreen.Companion.trackDetailsRoute(track: Track): String {
    val encodedName = Uri.encode(track.trackName)
    val encodedArtist = Uri.encode(track.artistName)
    val encodedTime = Uri.encode(track.trackTime)
    return "${AppScreen.TrackDetails.baseRoute}/${track.id}/$encodedName/$encodedArtist/$encodedTime"
}

fun AppScreen.Companion.playlistRoute(playlistId: Long): String {
    return "${AppScreen.Playlist.baseRoute}/$playlistId"
}