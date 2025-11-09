package com.example.playlist_maker_android_nadtochievatatyana.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import com.example.playlist_maker_android_nadtochievatatyana.ui.navigation.PlaylistHost
import com.example.playlist_maker_android_nadtochievatatyana.ui.search.SearchScreen
import com.example.playlist_maker_android_nadtochievatatyana.ui.search.SearchViewModel
import com.example.playlist_maker_android_nadtochievatatyana.ui.theme.PlaylistMakerTheme


class MainActivity : ComponentActivity() {
    private val searchViewModel by viewModels<SearchViewModel> {
        SearchViewModel.getViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlaylistMakerTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    PlaylistHost(navController = navController)
                }
            }
        }
    }
}