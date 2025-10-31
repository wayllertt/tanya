package com.example.playlist_maker_android_nadtochievatatyana.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.playlist_maker_android_nadtochievatatyana.ui.navigation.PlaylistHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            PlaylistHost(navController = navController)
        }
    }
}

@Composable
fun MainScreen(
    onOpenSearch: () -> Unit,
    onOpenSettings: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9)),
        verticalArrangement = Arrangement.Top,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFF3D6EFF),
                    shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp),
                )
                .padding(vertical = 20.dp, horizontal = 16.dp),
        ) {
            Text(
                text = "Playlist maker",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        DrawerItem(icon = Icons.Default.Search, text = "Поиск") { onOpenSearch() }
        DrawerItem(icon = Icons.Default.PlayArrow, text = "Плейлисты") {
            Toast.makeText(context, "Кнопка нажата", Toast.LENGTH_SHORT).show()
        }
        DrawerItem(icon = Icons.Default.FavoriteBorder, text = "Избранное") {
            Toast.makeText(context, "Кнопка нажата", Toast.LENGTH_SHORT).show()
        }
        DrawerItem(icon = Icons.Default.Settings, text = "Настройки") { onOpenSettings() }
    }
}

@Composable
fun DrawerItem(
    icon: ImageVector,
    text: String,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick?.invoke() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black.copy(alpha = 0.85f),
            modifier = Modifier.size(22.dp),
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = text,
            color = Color.Black.copy(alpha = 0.9f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f),
        )
    }
}