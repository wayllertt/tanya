package com.example.playlist_maker_android_nadtochievatatyana.creator

import android.content.res.Resources
import com.example.playlist_maker_android_nadtochievatatyana.R
import com.example.playlist_maker_android_nadtochievatatyana.data.dto.TrackDto
import java.util.Locale

class Storage(private val resources: Resources) {
    private val listTracks = listOf(
        TrackDto(
            trackName = resources.getString(R.string.track_vladivostok_2000),
            artistName = resources.getString(R.string.artist_mumiy_troll),
            trackTimeMillis = 158000,
        ),
        TrackDto(
            trackName = resources.getString(R.string.track_gruppa_krovi),
            artistName = resources.getString(R.string.artist_kino),
            trackTimeMillis = 283000,
        ),
        TrackDto(
            trackName = resources.getString(R.string.track_ne_smotri_nazad),
            artistName = resources.getString(R.string.artist_ariya),
            trackTimeMillis = 312000,
        ),
        TrackDto(
            trackName = resources.getString(R.string.track_zvezda_po_imeni_solnce),
            artistName = resources.getString(R.string.artist_kino),
            trackTimeMillis = 225000,
        ),
        TrackDto(
            trackName = resources.getString(R.string.track_london),
            artistName = resources.getString(R.string.artist_akvarium),
            trackTimeMillis = 272000,
        ),
        TrackDto(
            trackName = resources.getString(R.string.track_na_zare),
            artistName = resources.getString(R.string.artist_alyans),
            trackTimeMillis = 230000,
        ),
        TrackDto(
            trackName = resources.getString(R.string.track_peremen),
            artistName = resources.getString(R.string.artist_kino),
            trackTimeMillis = 296000,
        ),
        TrackDto(
            trackName = resources.getString(R.string.track_rozovyy_flamingo),
            artistName = resources.getString(R.string.artist_splin),
            trackTimeMillis = 195000,
        ),
        TrackDto(
            trackName = resources.getString(R.string.track_tancevat),
            artistName = resources.getString(R.string.artist_melnitsa),
            trackTimeMillis = 222000,
        ),
        TrackDto(
            trackName = resources.getString(R.string.track_chernyy_bumer),
            artistName = resources.getString(R.string.artist_serega),
            trackTimeMillis = 241000,
        ),
    )

    fun search(request: String): List<TrackDto> {
        val normalizedRequest = request.normalizeForSearch()
        if (normalizedRequest.isEmpty()) return emptyList()
        return listTracks.filter { track ->
            track.trackName.normalizeForSearch().contains(normalizedRequest)
        }
    }
    private fun String.normalizeForSearch(): String =
        lowercase(Locale.getDefault())
            .replace('ё', 'е')
            .trim()
}