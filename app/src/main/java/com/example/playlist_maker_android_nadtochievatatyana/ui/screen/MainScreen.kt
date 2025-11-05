package com.example.playlist_maker_android_nadtochievatatyana.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(
    onOpenSearch: () -> Unit,
    onOpenPlaylists: () -> Unit,
    onOpenFavorites: () -> Unit,
    onOpenSettings: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.Start) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color(0xFF4F7AD8))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Playlist maker",
                    color = Color.White,
                    fontSize = 28.sp,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
            Spacer(Modifier.height(24.dp))
            Surface(
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                Column {
                    MainMenuItem(
                        icon = Icons.Default.Search,
                        text = "Поиск",
                        onClick = onOpenSearch
                    )
                    Divider()
                    MainMenuItem(
                        icon = Icons.Default.PlayArrow,
                        text = "Плейлисты",
                        onClick = onOpenPlaylists
                    )
                    Divider()
                    MainMenuItem(
                        icon = Icons.Default.Favorite,
                        text = "Избранное",
                        onClick = onOpenFavorites
                    )
                    Divider()
                    MainMenuItem(
                        icon = Icons.Default.Settings,
                        text = "Настройки",
                        onClick = onOpenSettings
                    )
                }
            }
        }
    }
}

@Composable
fun MainMenuItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Icon(icon, contentDescription = text, tint = Color.Black, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(15.dp))
        Text(text, fontSize = 20.sp)
    }
}
