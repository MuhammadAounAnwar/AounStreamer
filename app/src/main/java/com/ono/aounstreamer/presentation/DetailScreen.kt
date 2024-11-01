package com.ono.aounstreamer.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ono.streamerlibrary.domain.model.MediaItem

@Composable
fun DetailScreen(mediaItem: MediaItem = MediaItem(), onItemSelected: (String) -> Unit) {
    Column {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${mediaItem.posterPath}",
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            contentScale = ContentScale.Crop
        )
        Text(text = mediaItem.title ?: "Unknown", style = MaterialTheme.typography.headlineMedium)
        Text(text = mediaItem.overview ?: "Unknown", style = MaterialTheme.typography.headlineSmall)
        Button(onClick = { onItemSelected.invoke(mediaItem.backdropPath ?: "") }) {
            Text(text = "Watch")
        }
    }
}
