package com.example.playlist_maker_android_nadtochievatatyana.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.example.playlist_maker_android_nadtochievatatyana.ui.activity.SettingsScreen
import com.example.playlist_maker_android_nadtochievatatyana.ui.activity.MainScreen
import com.example.playlist_maker_android_nadtochievatatyana.ui.search.SearchRoute
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.playlist_maker_android_nadtochievatatyana.ui.settings.SettingsScreen



@Composable
fun PlaylistHost(navController: NavHostController) {
    val navigateUp: () -> Unit = { navController.navigateUp() }

    val navigateToSearch: () -> Unit = {
        navController.navigate(
            AppScreen.Search.route,
            navOptions { launchSingleTop = true },
        )
    }

    val navigateToSettings: () -> Unit = {
        navController.navigate(
            AppScreen.Settings.route,
            navOptions { launchSingleTop = true },
        )
    }

    NavHost(
        navController = navController,
        startDestination = AppScreen.Main.route,
    ) {
        composable(AppScreen.Main.route) {
            MainScreen(
                onOpenSearch = navigateToSearch,
                onOpenSettings = navigateToSettings,
            )
        }
        composable(AppScreen.Search.route) {
            SearchRoute(
                modifier = Modifier.fillMaxSize(),
            )
        }
        composable(AppScreen.Settings.route) {
            SettingsScreen(
                onBack = navigateUp,
            )
        }
    }
}