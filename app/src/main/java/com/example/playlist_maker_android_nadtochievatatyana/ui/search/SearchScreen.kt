package com.example.playlist_maker_android_nadtochievatatyana.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.playlist_maker_android_nadtochievatatyana.R
import com.example.playlist_maker_android_nadtochievatatyana.domain.models.Track
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardActions
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage

@Composable
fun TrackListItem(
    track: Track,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) {
                    Modifier.clickable { onClick() }
                } else {
                    Modifier
                },
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        AsyncImage(
            painter = painterResource(id = R.drawable.ic_music),
            modifier = Modifier
                .size(64.dp)
                .clip(shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)),
            model = track.artworkUrl100,
            contentDescription = stringResource(
                R.string.content_description_track,
                track.trackName,
            ),
            placeholder = painterResource(id = R.drawable.ic_music),
            error = painterResource(id = R.drawable.ic_music),
            fallback = painterResource(id = R.drawable.ic_music),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(track.trackName, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(track.artistName, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Column(
            horizontalAlignment = Alignment.End,
        ) {
            Text(track.trackTime)
        }
    }
}

@Composable
fun SearchRoute(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onTrackClick: (Track) -> Unit,
    viewModel: SearchViewModel = viewModel(
        factory = SearchViewModel.getViewModelFactory(LocalContext.current),
    ),
) {
    SearchScreen(
        modifier = modifier,
        viewModel = viewModel,
        onBack = onBack,
        onTrackClick = onTrackClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    onBack: () -> Unit,
    onTrackClick: (Track) -> Unit,
) {
    val screenState by viewModel.searchScreenState.collectAsState()
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(
                    top = dimensionResource(id = R.dimen.search_screen_content_padding_top),
                    start = dimensionResource(id = R.dimen.search_screen_content_padding_horizontal),
                    end = dimensionResource(id = R.dimen.search_screen_content_padding_horizontal),
                )
                .fillMaxSize(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.content_description_back),
                    )
                }
                Text(
                    text = stringResource(id = R.string.search_title),
                    fontWeight = FontWeight.Bold,
                )
            }

            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    if (it.isBlank()) {
                        viewModel.reset()
                    } else {
                        viewModel.search(it)
                    }
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.clickable(enabled = text.isNotBlank()) {
                            focusManager.clearFocus()
                            viewModel.search(text)
                        },
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.content_description_search),
                    )
                },
                trailingIcon = {
                    if (text.isNotBlank()) {
                        Icon(
                            modifier = Modifier.clickable {
                                text = ""
                                viewModel.reset()
                                focusManager.clearFocus()
                            },
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(R.string.content_description_clear),
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    focusManager.clearFocus()
                    viewModel.search(text)
                }),
                placeholder = {
                    Text(text = stringResource(R.string.search_placeholder))
                },
            )

            when (screenState) {
                is SearchState.Initial -> {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = stringResource(R.string.search_prompt))
                    }
                }

                is SearchState.Searching -> {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is SearchState.Success -> {
                    val tracks = (screenState as SearchState.Success).tracks
                    if (tracks.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_placeholder_empty),
                                    contentDescription = null,
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                            Text(text = stringResource(id = R.string.search_error_empty))
                        }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                        ) {
                            itemsIndexed(tracks) { index, track ->
                                TrackListItem(track = track, onClick = { onTrackClick(track) })
                                if (index != tracks.lastIndex) {
                                    HorizontalDivider(
                                        thickness = dimensionResource(id = R.dimen.search_screen_divider_thickness),
                                        color = Color.LightGray,
                                    )
                                }
                            }
                        }
                    }
                }

                is SearchState.Fail -> {
                    val error = (screenState as SearchState.Fail).messageResId
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_placeholder_error),
                                contentDescription = null,
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = stringResource(error))
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = viewModel::repeatLastSearch) {
                                Text(text = stringResource(id = R.string.search_action_retry))
                            }
                        }
                    }
                }
            }
        }
    }
}