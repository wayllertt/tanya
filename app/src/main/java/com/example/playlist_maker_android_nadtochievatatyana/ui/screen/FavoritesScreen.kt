package com.example.playlist_maker_android_nadtochievatatyana.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playlist_maker_android_nadtochievatatyana.R
import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Track
import com.example.playlist_maker_android_nadtochievatatyana.ui.playlist.PlaylistViewModel
import com.example.playlist_maker_android_nadtochievatatyana.ui.search.TrackListItem

@Composable
fun FavoritesScreen(
    onBack: () -> Unit,
    onTrackClick: (Track) -> Unit,
    playlistViewModel: PlaylistViewModel = viewModel(
        factory = PlaylistViewModel.getViewModelFactory(LocalContext.current),
    ),
) {
    val favoriteList by playlistViewModel.favoriteList.collectAsState(emptyList())

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
                        contentDescription = stringResource(id = R.string.content_description_back),
                    )
                }
                Text(text = stringResource(id = R.string.menu_favorites), fontWeight = FontWeight.Bold)
            }
        },
    ) { innerPadding ->
        if (favoriteList.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = stringResource(id = R.string.empty_favorites))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            ) {
                items(favoriteList.size) { index ->
                    TrackListItem(
                        track = favoriteList[index],
                        onClick = { onTrackClick(favoriteList[index]) },
                        onLongClick = {
                            playlistViewModel.toggleFavorite(favoriteList[index], false)
                        },
                    )
                    HorizontalDivider(thickness = 0.5.dp)
                }
            }
        }
    }
}