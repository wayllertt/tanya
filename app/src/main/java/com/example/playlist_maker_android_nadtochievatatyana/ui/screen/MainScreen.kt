package com.example.playlist_maker_android_nadtochievatatyana.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_nadtochievatatyana.R

@Composable
fun MainScreen(
    onOpenSearch: () -> Unit,
    onOpenPlaylists: () -> Unit,
    onOpenFavorites: () -> Unit,
    onOpenSettings: () -> Unit,
    onCreatePlaylist: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreatePlaylist,
                containerColor = Color(0xFF3D6EFF),
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.fab_content_description),
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF9F9F9))
            ) {
                Header(title = stringResource(id = R.string.playlist_maker))

                Spacer(
                    Modifier.height(
                        dimensionResource(id = R.dimen.main_screen_spacing_between_items)
                    )
                )

                MainMenuItem(
                    icon = Icons.Filled.Search,
                    text = stringResource(id = R.string.search_title),
                    onClick = onOpenSearch
                )

                MainMenuItem(
                    icon = Icons.Filled.PlayArrow,
                    text = stringResource(id = R.string.menu_playlists),
                    onClick = onOpenPlaylists
                )

                MainMenuItem(
                    icon = Icons.Filled.Favorite,
                    text = stringResource(id = R.string.menu_favorites),
                    onClick = onOpenFavorites
                )

                MainMenuItem(
                    icon = Icons.Filled.Settings,
                    text = stringResource(id = R.string.settings_title),
                    onClick = onOpenSettings
                )
            }
        }
    }
}


@Composable
private fun Header(title: String) {
    Box(
        modifier = Modifier
            .background(
                color = Color(0xFF3D6EFF),
                shape = RoundedCornerShape(
                    bottomStart = dimensionResource(id = R.dimen.main_screen_header_corner_radius),
                    bottomEnd = dimensionResource(id = R.dimen.main_screen_header_corner_radius),
                )
            )
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.main_screen_header_horizontal_padding),
                vertical = dimensionResource(id = R.dimen.main_screen_header_vertical_padding),
            )
    ) {
        val headerTextSize = dimensionResource(id = R.dimen.main_screen_header_text_size)
        Text(
            text = title,
            color = Color.White,
            fontSize = headerTextSize.value.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MainMenuItem(icon: ImageVector, text: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(dimensionResource(id = R.dimen.main_screen_menu_item_padding))
    ) {
        Icon(
            icon,
            contentDescription = text,
            tint = Color.Black,
            modifier = Modifier.size(dimensionResource(id = R.dimen.main_screen_menu_item_icon_size))
        )
        Spacer(Modifier.width(dimensionResource(id = R.dimen.main_screen_menu_item_icon_spacing)))
        val menuItemTextSize = dimensionResource(id = R.dimen.main_screen_menu_item_text_size)
        Text(text, fontSize = menuItemTextSize.value.sp)
    }
}

//
//@Composable
//fun MainScreen(
//    onOpenSearch: () -> Unit,
//    onOpenPlaylists: () -> Unit,
//    onOpenFavorites: () -> Unit,
//    onOpenSettings: () -> Unit,
//) {
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFF9F9F9))
//    ) {
//        Header(title = stringResource(id = R.string.playlist_maker))
//        Spacer(Modifier.height(10.dp))
//
//        MenuRow(icon = Icons.Default.Search, text = stringResource(R.string.search)) {
//            onOpenSearch()
//        }
//        MenuRow(icon = Icons.Default.PlayArrow, text = stringResource(R.string.playlists)) {
//            onOpenPlaylists()
//        }
//        MenuRow(icon = Icons.Default.FavoriteBorder, text = stringResource(R.string.favorites)) {
//            onOpenFavorites()
//        }
//        MenuRow(icon = Icons.Default.Settings, text = stringResource(R.string.settings_title)) {
//            onOpenSettings()
//        }
//    }
//}
//
//@Composable
//private fun Header(title: String) {
//    Box(
//        modifier = Modifier
//            .background(
//                color = Color(0xFF3D6EFF),
//                shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
//            )
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 30.dp)
//    ) {
//        Text(
//            text = title,
//            color = Color.White,
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Bold
//        )
//    }
//}
//
//@Composable
//fun MenuRow(
//    icon: ImageVector,
//    text: String,
//    onClick: (() -> Unit)? = null
//) {
//    val click = rememberUpdatedState(newValue = onClick)
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { click.value?.invoke() }
//            .padding(horizontal = 16.dp, vertical = 16.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = null,
//            tint = Color.Black.copy(alpha = 0.85f),
//            modifier = Modifier.size(30.dp)
//        )
//
//        Spacer(Modifier.width(20.dp))
//
//        Text(
//            text = text,
//            color = Color.Black.copy(alpha = 0.9f),
//            fontSize = 20.sp,
//            fontWeight = FontWeight.Medium,
//            modifier = Modifier.weight(1f)
//        )
//    }
//}
//
