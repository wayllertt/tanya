package com.example.playlist_maker_android_nadtochievatatyana.ui.screen

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.playlist_maker_android_nadtochievatatyana.R
import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Track
import com.example.playlist_maker_android_nadtochievatatyana.ui.playlist.PlaylistViewModel
import androidx.compose.ui.res.dimensionResource
import com.example.playlist_maker_android_nadtochievatatyana.domain.models.getArtworkUrlForSize
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetailsRoute(
    track: Track,
    onBack: () -> Unit,
    playlistViewModel: PlaylistViewModel = viewModel(
        factory = PlaylistViewModel.getViewModelFactory(LocalContext.current),
    ),
) {
    TrackDetailsScreen(
        track = track,
        playlistViewModel = playlistViewModel,
        onBack = onBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetailsScreen(
    track: Track,
    playlistViewModel: PlaylistViewModel,
    onBack: () -> Unit,
) {
    val playlists = playlistViewModel.playlists.collectAsStateWithLifecycle(emptyList())
    var currentTrack by remember(track.id) { mutableStateOf(track) }
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(track.id) {
        val stored = playlistViewModel.findTrack(track)
        if (stored != null) {
            currentTrack = stored
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.track_details_bottom_sheet_padding)),
            ) {
                Text(
                    text = stringResource(id = R.string.modal_playlists_title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
                if (playlists.value.isEmpty()) {
                    Text(
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.track_details_bottom_sheet_spacing)),
                        text = stringResource(id = R.string.no_playlists_for_track),
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.track_details_bottom_sheet_list_spacing)),
                    ) {
                        items(playlists.value) { playlist ->
                            PlaylistListItem(
                                playlist = playlist,
                                onClick = {
                                    playlistViewModel.insertSongToPlaylist(currentTrack, playlist.id)
                                    currentTrack = currentTrack.copy(playlistId = playlist.id)
                                    showBottomSheet = false
                                },
                                onLongClick = {},
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
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
                    text = stringResource(id = R.string.track_details_title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            }
        },
    ) {
        innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = dimensionResource(id = R.dimen.track_details_content_horizontal_padding))
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.track_details_content_spacing),
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.track_details_image_size))
                        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.track_details_image_corner_radius))),
                    model = currentTrack.getArtworkUrlForSize(512),
                    contentDescription = stringResource(
                        id = R.string.content_description_track,
                        currentTrack.trackName,
                    ),
                    placeholder = painterResource(id = R.drawable.ic_music),
                    error = painterResource(id = R.drawable.ic_music),
                    fallback = painterResource(id = R.drawable.ic_music),
                    contentScale = ContentScale.Crop,
                )
            }
            Text(
                text = currentTrack.trackName,
                fontSize = dimensionResource(id = R.dimen.track_details_title_text_size).value.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = currentTrack.artistName,
                fontSize = dimensionResource(id = R.dimen.track_details_artist_text_size).value.sp,
            )
            Text(text = stringResource(id = R.string.track_time_template, currentTrack.trackTime))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.track_details_action_spacing),
                ),
            ) {
                IconButton(onClick = {
                    val newFavorite = !currentTrack.favorite
                    playlistViewModel.toggleFavorite(currentTrack, newFavorite)
                    currentTrack = currentTrack.copy(favorite = newFavorite)
                }) {
                    Icon(
                        imageVector = if (currentTrack.favorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = stringResource(id = R.string.track_details_add_to_favorites),
                    )
                }
                IconButton(onClick = { showBottomSheet = true }) {
                    Icon(
                        imageVector = Icons.Filled.PlaylistAdd,
                        contentDescription = stringResource(id = R.string.track_details_add_to_playlist),
                    )
                }
            }
        }
    }
}