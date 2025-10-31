package com.example.playlist_maker_android_nadtochievatatyana

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions

@Composable
fun PlaylistHost(navController: NavHostController) {

    val navigateUp: () -> Unit = { navController.navigateUp() }

    val navigateToMain: () -> Unit = {
        navController.navigate(
            AppScreen.Main.route,
            navOptions {
                launchSingleTop = true
                popUpTo(navController.graph.startDestinationId) { inclusive = false }
            }
        )
    }

    val navigateToSearch: () -> Unit = {
        navController.navigate(
            AppScreen.Search.route,
            navOptions { launchSingleTop = true }
        )
    }

    val navigateToSettings: () -> Unit = {
        navController.navigate(
            AppScreen.Settings.route,
            navOptions { launchSingleTop = true }
        )
    }

    NavHost(
        navController = navController,
        startDestination = AppScreen.Main.route
    ) {
        composable(AppScreen.Main.route) {
            MainScreen(
                onOpenSearch = navigateToSearch,
                onOpenSettings = navigateToSettings
            )
        }
        composable(AppScreen.Search.route) {
            SearchScreen(
                onBack = navigateUp
            )
        }
        composable(AppScreen.Settings.route) {
            SettingsScreen(
                onBack = navigateUp
            )
        }
    }
}