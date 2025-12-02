package com.example.playlist_maker_android_nadtochievatatyana.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playlist_maker_android_nadtochievatatyana.R
import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Playlist
import com.example.playlist_maker_android_nadtochievatatyana.ui.playlist.PlaylistViewModel

@Composable
fun PlaylistListItem(
    playlist: Playlist,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Image(
            modifier = Modifier.size(48.dp),
            painter = painterResource(id = R.drawable.ic_music),
            contentDescription = playlist.name,
            colorFilter = ColorFilter.tint(Color.Gray),
        )
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(playlist.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            val text = stringResource(id = R.string.playlists_tracks_count, playlist.tracks.size)
            Text(text, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun PlaylistsScreen(
    onBack: () -> Unit,
    onCreatePlaylist: () -> Unit,
    onPlaylistClick: (Playlist) -> Unit,
    playlistViewModel: PlaylistViewModel = viewModel(
        factory = PlaylistViewModel.getViewModelFactory(LocalContext.current),
    ),
) {
    val playlistsState = playlistViewModel.playlists.collectAsStateWithLifecycle(emptyList())

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.content_description_back),
                    )
                }
                Text(
                    text = stringResource(id = R.string.menu_playlists),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreatePlaylist,
                shape = CircleShape,
                containerColor = Color.Gray,
                contentColor = Color.White,
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(R.string.fab_content_description))
            }
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White),
        ) {
            if (playlistsState.value.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = stringResource(id = R.string.empty_playlists), color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                ) {
                    items(playlistsState.value) { playlist ->
                        PlaylistListItem(playlist = playlist) { onPlaylistClick(playlist) }
                        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                    }
                }
            }
        }
    }
}