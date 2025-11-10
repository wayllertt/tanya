package com.example.playlist_maker_android_nadtochievatatyana.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.playlist_maker_android_nadtochievatatyana.R

@Composable
fun PlaylistsScreen(onBack: () -> Unit) {
    val backgroundColor = colorResource(R.color.white)

    Scaffold(
        containerColor = backgroundColor,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(backgroundColor)
        )
    }
}