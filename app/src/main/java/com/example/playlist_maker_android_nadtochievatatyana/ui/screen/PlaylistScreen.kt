package com.example.playlist_maker_android_nadtochievatatyana.ui.screen

import androidx.compose.material.icons.filled.Delete
import android.net.Uri
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.playlist_maker_android_nadtochievatatyana.R
import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Track
import com.example.playlist_maker_android_nadtochievatatyana.ui.playlist.PlaylistViewModel
import com.example.playlist_maker_android_nadtochievatatyana.ui.search.TrackListItem
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp

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
                onDelete = playlistState?.let { playlist ->
                    {
                        playlistViewModel.deletePlaylistById(playlist.id)
                        onBack()
                    }
                },
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
                        .padding(horizontal = dimensionResource(id = R.dimen.playlist_content_horizontal_padding)),
                    verticalArrangement = Arrangement.spacedBy(
                        dimensionResource(id = R.dimen.playlist_content_vertical_spacing),
                    ),
                ) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(
                                dimensionResource(id = R.dimen.playlist_header_vertical_spacing),
                            ),
                        ) {
                            PlaylistHeader(coverImageUri = playlist.coverImageUri)
                            Text(
                                text = playlist.name,
                                fontSize = dimensionResource(id = R.dimen.playlist_title_text_size).value.sp,
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
                                fontSize = dimensionResource(id = R.dimen.playlist_tracks_count_text_size).value.sp,
                            )
                            Spacer(
                                modifier = Modifier.height(
                                    dimensionResource(id = R.dimen.playlist_header_divider_spacing),
                                ),
                            )
                            HorizontalDivider(
                                thickness = dimensionResource(id = R.dimen.common_divider_thickness),
                                color = Color.LightGray,
                            )
                        }
                    }
                    if (playlist.tracks.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = dimensionResource(id = R.dimen.playlist_empty_state_vertical_padding)),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(text = stringResource(id = R.string.playlist_empty_tracks), color = Color.Gray)
                            }
                        }
                    } else {
                        items(playlist.tracks) { track ->
                            TrackListItem(
                                track = track,
                                modifier = Modifier.padding(
                                    vertical = dimensionResource(id = R.dimen.playlist_track_item_vertical_padding),
                                ),
                                onClick = { onTrackClick(track) },
                            )
                            HorizontalDivider(
                                thickness = dimensionResource(id = R.dimen.common_divider_thickness),
                                color = Color.LightGray,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaylistHeader(coverImageUri: String?) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            modifier = Modifier.size(dimensionResource(id = R.dimen.playlist_header_cover_size)),
            model = coverImageUri?.let { Uri.parse(it) },
            placeholder = painterResource(id = R.drawable.ic_music),
            error = painterResource(id = R.drawable.ic_music),
            fallback = painterResource(id = R.drawable.ic_music),
            contentDescription = stringResource(id = R.string.playlist_cover),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun RowTopBar(onBack: () -> Unit, title: String, onDelete: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.top_bar_padding_horizontal),
                vertical = dimensionResource(id = R.dimen.top_bar_padding_vertical),
            ),
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
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            fontSize = dimensionResource(id = R.dimen.top_bar_title_text_size).value.sp,
        )
        onDelete?.let { deleteAction ->
            IconButton(onClick = deleteAction) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(id = R.string.content_description_delete_playlist),
                )
            }
        }
    }
}