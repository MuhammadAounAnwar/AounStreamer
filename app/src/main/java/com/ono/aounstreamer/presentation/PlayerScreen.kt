package com.ono.aounstreamer.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter

@Composable
fun PlayerScreen(item: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${item}"),
            contentDescription = item,
            modifier = Modifier.fillMaxSize()
        )
    }
}
