package com.ono.aounstreamer.presentation.screen

import android.util.Log
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun PosterScreen(posterUrl: String) {
    /*Column(modifier = Modifier.fillMaxSize()) {

    }*/


    Log.d("PosterScreen", "PosterScreen: $posterUrl")
    Log.d("PosterScreen", "PosterScreen: ${"https://image.tmdb.org/t/p/w500${posterUrl}"}")

    AsyncImage(
        model = "https://image.tmdb.org/t/p/w500/${posterUrl}",
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentScale = ContentScale.Crop
    )
}