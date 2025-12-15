package com.example.playlist_maker_android_nadtochievatatyana.domain.models

data class Track(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl100: String? = null,
    val trackTimeMillis: Long? = null,
    val favorite: Boolean = false,
)

fun Track.getArtworkUrlForSize(size: Int): String? {
    val originalUrl = artworkUrl100 ?: return null
    val separatorIndex = originalUrl.lastIndexOf('/')
    val newFileName = "${size}x${size}bb.jpg"

    return if (separatorIndex != -1) {
        originalUrl.substring(0, separatorIndex + 1) + newFileName
    } else {
        originalUrl
    }
}