package com.example.playlist_maker_android_nadtochievatatyana.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.playlist_maker_android_nadtochievatatyana.ui.screen.SettingsScreen
import com.example.playlist_maker_android_nadtochievatatyana.ui.screen.MainScreen
import com.example.playlist_maker_android_nadtochievatatyana.ui.search.SearchRoute
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.playlist_maker_android_nadtochievatatyana.ui.screen.FavoritesScreen
import com.example.playlist_maker_android_nadtochievatatyana.ui.screen.PlaylistsScreen


@Composable
fun PlaylistHost(navController: NavHostController) {
    val navigateUp: () -> Unit = { navController.navigateUp() }

    NavHost(
        navController = navController,
        startDestination = AppScreen.Main.route,
    ) {
        composable(AppScreen.Main.route) {
            MainScreen(
                onOpenSearch = {navController.navigate(AppScreen.Search.route)},
                onOpenPlaylists = {navController.navigate(route=AppScreen.Playlists.route)},
                onOpenFavorites = {navController.navigate(route=AppScreen.Favorites.route)},
                onOpenSettings = {navController.navigate(AppScreen.Settings.route)},
            )
        }
        composable(AppScreen.Search.route) {
            SearchRoute(
                modifier = Modifier.fillMaxSize(),
            )
        }
        composable(AppScreen.Playlists.route) {
            PlaylistsScreen(
                onBack = navigateUp,
            )
        }
        composable(AppScreen.Favorites.route) {
            FavoritesScreen(
                onBack = navigateUp,
            )
        }
        composable(AppScreen.Settings.route) {
            SettingsScreen(
                onBack = navigateUp,
            )
        }
    }
}