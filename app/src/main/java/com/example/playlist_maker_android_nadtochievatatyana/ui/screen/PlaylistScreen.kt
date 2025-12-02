package com.example.playlist_maker_android_nadtochievatatyana.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.playlist_maker_android_nadtochievatatyana.R
import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Track
import com.example.playlist_maker_android_nadtochievatatyana.ui.playlist.PlaylistViewModel
import com.example.playlist_maker_android_nadtochievatatyana.ui.search.TrackListItem

@Composable
fun PlaylistRoute(
    playlistId: Long,
    onBack: () -> Unit,
    onTrackClick: (Track) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PlaylistViewModel = viewModel(
        factory = PlaylistViewModel.getViewModelFactory(LocalContext.current, playlistId),
    ),
) {
    PlaylistScreen(
        modifier = modifier,
        playlistViewModel = viewModel,
        onBack = onBack,
        onTrackClick = onTrackClick,
    )
}

@Composable
fun PlaylistScreen(
    modifier: Modifier,
    playlistViewModel: PlaylistViewModel,
    onBack: () -> Unit,
    onTrackClick: (Track) -> Unit,
) {
    val playlistState by playlistViewModel.playlist.collectAsStateWithLifecycle(null)

    Scaffold(
        topBar = {
            RowTopBar(
                onBack = onBack,
                title = playlistState?.name ?: stringResource(id = R.string.menu_playlists),
            )
        },
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            val playlist = playlistState
            if (playlist == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            PlaylistHeader()
                            Text(
                                text = playlist.name,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            if (playlist.description.isNotBlank()) {
                                Text(text = playlist.description)
                            }
                            Text(
                                text = stringResource(
                                    id = R.string.playlists_tracks_count,
                                    playlist.tracks.size,
                                ),
                                color = Color.Gray,
                                fontSize = 12.sp,
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                        }
                    }
                    if (playlist.tracks.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(text = stringResource(id = R.string.playlist_empty_tracks), color = Color.Gray)
                            }
                        }
                    } else {
                        items(playlist.tracks) { track ->
                            TrackListItem(
                                track = track,
                                modifier = Modifier.padding(vertical = 4.dp),
                                onClick = { onTrackClick(track) },
                            )
                            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaylistHeader() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            modifier = Modifier.size(160.dp),
            model = null,
            placeholder = painterResource(id = R.drawable.ic_music),
            error = painterResource(id = R.drawable.ic_music),
            fallback = painterResource(id = R.drawable.ic_music),
            contentDescription = null,
        )
    }
}

@Composable
private fun RowTopBar(onBack: () -> Unit, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(id = R.string.content_description_back),
            )
        }
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
    }
}