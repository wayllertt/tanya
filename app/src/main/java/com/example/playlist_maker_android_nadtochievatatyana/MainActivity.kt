package com.example.playlist_maker_android_nadtochievatatyana

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            PlaylistHost(navController = navController)
        }
    }

//        val intent = Intent(this, SettingsActivity::class.java)
//        startActivity(intent)
//
//        val displayIntent = Intent(this, MessageActivity::class.java)
//        startActivity(displayIntent)

    }
    @Composable
    fun MainScreen(onOpenSearch: () -> Unit, onOpenSettings: () -> Unit) {

        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9F9F9))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF3D6EFF), RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                    .padding(vertical = 20.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = "Playlist maker",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            DrawerItem(icon = Icons.Default.Search, text = "Поиск") {
                val intent = Intent(context, SearchActivity::class.java)
                context.startActivity(intent)
            }
            DrawerItem(icon = Icons.Default.PlayArrow, text = "Плейлисты") {
                Toast.makeText(context, "Кнопка нажата", Toast.LENGTH_SHORT).show()
            }
            DrawerItem(icon = Icons.Default.FavoriteBorder, text = "Избранное") {
                Toast.makeText(context, "Кнопка нажата", Toast.LENGTH_SHORT).show()
            }
            DrawerItem(icon = Icons.Default.Settings, text = "Настройки") {
                val intent = Intent(context, SettingsActivity::class.java)
                context.startActivity(intent)
            }

        }
        }


    @Composable
    fun DrawerItem(
        icon: ImageVector,
        text: String,
        onClick: (() -> Unit)? = null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick?.invoke() }
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Black.copy(alpha = 0.85f),
                modifier = Modifier.size(22.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = text,
                color = Color.Black.copy(alpha = 0.9f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )

//            Icon(
//                imageVector = Icons.Default.ChevronRight,
//                contentDescription = null,
//                tint = Color.Gray,
//                modifier = Modifier.size(20.dp)
//            )
        }
    }



//    fun PanelHeader() {
//        Text(
//            text = stringResource(id = R.string.playlist_maker),
//            style = TextStyle (
//                fontSize = 22.sp,
////                color = R.color.white,
////                fontStyle = FontStyle.YS_Display
//                )
//        )
//    }
//    @Preview(name = "portrait", showSystemUi = true)
//    @Composable
//    fun ButtonSearch(){
//        Button (
//            modifier = Modifier.fillMaxWidth(),
//            enabled = true,
//            onClick = {Log.d("ButtonSearch", "Кнопка нажата")},
//            content = {Text(text = "Поиск")} ,
////            elevation = ButtonDefaults.elevation(defaultElevation = 8.dp),
//         //   color = R.color.black,
////            colors = ButtonDefaults.buttonColors(backgroundColor = R.color.white),
//        )
//    }
//
//    @Preview(name = "portrait", showSystemUi = true)
//    @Composable
//    fun ButtonPlaylists(){
//        Button (
//            enabled = true,
//            modifier = Modifier.fillMaxWidth(),
//            onClick = {Log.d("ButtonPlaylists", "Кнопка нажата")},
//            content = {Text(text = "Плейлисты")},
////            elevation = ButtonDefaults.elevation(defaultElevation = 8.dp),
////            colors = ButtonDefaults.buttonColors(backgroundColor = R.color.white),
//        )
//    }
//
//    @Preview(name = "portrait", showSystemUi = true)
//    @Composable
//    fun ButtonFavorites(){
//        Button (
//            enabled = true,
//            modifier = Modifier.fillMaxWidth(),
//            onClick = {Log.d("ButtonFavorites", "Кнопка нажата")},
//            content = {Text(text = "Избранное")}
//            //colors = ButtonDefaults.buttonColors(backgroundColor = R.color.white),
//        )
//    }
//
//    @Preview(name = "portrait", showSystemUi = true)
//    @Composable
//    fun ButtonSettings(){
//        Button (
//            enabled = true,
//            modifier = Modifier.fillMaxWidth(),
//            onClick = {Log.d("ButtonSettings", "Кнопка нажата")},
//            content = {Text(text = "Настройки")},
////            elevation = ButtonDefaults.elevation(defaultElevation = 8.dp),
////            colors = ButtonDefaults.buttonColors(backgroundColor = R.color.white),
//        )
//    }


//    fun TextPreview() {
//        Text("Playlist Maker")
////        style = TextStyle(
////            fontSize = 22.sp,
////        )
//    }
//
//    fun ButtonSearch(){
//
//    }






