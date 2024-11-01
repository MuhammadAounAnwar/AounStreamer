package com.ono.aounstreamer

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ono.streamerlibrary.domain.model.MediaItem

@Composable
fun DetailScreen(mediaId: String, onItemSelected: (String) -> Unit) {
    val item = MediaItem(
        id = mediaId.toInt(),
        title = "Example Title",
        overview = "Example Overview",
        posterPath = "/example.jpg",
        mediaType = "movie"
    )

    Column {
        Text(text = item.title ?: "Unknown", style = MaterialTheme.typography.headlineSmall)
        item.overview?.let { Text(text = it) }
        Button(onClick = { onItemSelected.invoke(item.backdropPath!!) }) {
            Text(text = "Watch")
        }
    }
}
