package com.ono.aounstreamer.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ono.aounstreamer.MainViewModel
import com.ono.aounstreamer.util.ShowToast

@Composable
fun DetailScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onWatchPosterClicked: (String) -> Unit,
    onWatchVideoClicked: () -> Unit
) {

    val TAG = "DetailScreen"
    val context = LocalContext.current

    val scrollState = rememberScrollState()
    viewModel.selectedItem?.let { mediaItem ->
        Column {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${mediaItem.posterPath}",
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3F),
                contentScale = ContentScale.Crop
            )
            Text(
                text = if (mediaItem.mediaType == "tv") mediaItem.name else mediaItem.title,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.verticalScroll(scrollState),
                text = mediaItem.overview ?: "Unknown",
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    if (mediaItem.posterPath != null) {
                        onWatchPosterClicked.invoke(mediaItem.posterPath ?: "")
                    } else {
                        context.ShowToast("Poster not available")
                    }
                }) {
                    Text(text = "Watch Poster")
                }


                Button(onClick = {
                    onWatchVideoClicked.invoke()
                }) {
                    Text(text = "Watch Video")
                }
            }
        }
    }
}

