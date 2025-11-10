package com.example.playlist_maker_android_nadtochievatatyana.ui.screen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.playlist_maker_android_nadtochievatatyana.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(onBack: () -> Unit) {
    val context = LocalContext.current

    val shareText = stringResource(R.string.share_text)
    val shareMimeType = stringResource(R.string.share_mime_type)
    val emailTo = stringResource(R.string.dev_email_to)
    val emailSubject = stringResource(R.string.dev_email_subject)
    val emailBody = stringResource(R.string.dev_email_body)
    val offerUrl = stringResource(R.string.offer_url)
    val mailToUri = stringResource(R.string.mailto_scheme, emailTo)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings_title),
                        fontWeight = FontWeight.Medium,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.content_description_back),
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = dimensionResource(id = R.dimen.settings_screen_theme_row_vertical_padding)
                    )
            ) {
                Text(
                    text = stringResource(R.string.dark_theme),
                    modifier = Modifier.weight(1f),
                    .clickable{},
                )
                Switch(checked = false, onCheckedChange = null, enabled = false)
            }
            HorizontalDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = shareMimeType
                            putExtra(Intent.EXTRA_TEXT, shareText)
                        }
                        val chooser = Intent.createChooser(
                            shareIntent,
                            context.getString(R.string.share_chooser_title),
                        )
                        try {
                            context.startActivity(chooser)
                        } catch (_: ActivityNotFoundException) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.no_apps_found),
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.settings_screen_row_horizontal_padding),
                        vertical = dimensionResource(id = R.dimen.settings_screen_row_vertical_padding),
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.btn_share_app),
                    fontSize = dimensionResource(id = R.dimen.settings_screen_text_size).value.sp,
                    modifier = Modifier.weight(1f),
                )
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = null,
                )
            }

            HorizontalDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse(mailToUri)).apply {
                            putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                            putExtra(Intent.EXTRA_TEXT, emailBody)
                        }
                        try {
                            context.startActivity(emailIntent)
                        } catch (_: ActivityNotFoundException) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.no_mail_clients),
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.settings_screen_row_horizontal_padding),
                        vertical = dimensionResource(id = R.dimen.settings_screen_row_vertical_padding),
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.btn_write_to_devs),
                    fontSize = dimensionResource(id = R.dimen.settings_screen_text_size).value.sp,
                    modifier = Modifier.weight(1f),
                )
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = null,
                )
            }

            HorizontalDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(offerUrl))
                        try {
                            context.startActivity(intent)
                        } catch (_: ActivityNotFoundException) {
                            Toast.makeText(
                                context,
                                context.getString(R.string.no_browser_found),
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.settings_screen_row_horizontal_padding),
                        vertical = dimensionResource(id = R.dimen.settings_screen_row_vertical_padding),
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.btn_user_agreement),
                    fontSize = dimensionResource(id = R.dimen.settings_screen_text_size).value.sp,
                    modifier = Modifier.weight(1f),
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                )
            }

            HorizontalDivider()
        }
    }
}