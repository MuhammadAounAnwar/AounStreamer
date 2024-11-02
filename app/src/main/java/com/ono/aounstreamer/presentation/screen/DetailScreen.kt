package com.ono.aounstreamer.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
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
    val context = LocalContext.current

    viewModel.selectedItem?.let { mediaItem ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${mediaItem.posterPath}",
                    contentDescription = "Media Item Poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))


            Text(
                text = if (mediaItem.mediaType == "tv") mediaItem.name else mediaItem.title,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.semantics { contentDescription = "Item Title" }
            )

            Spacer(modifier = Modifier.height(8.dp))


            val scrollState = rememberScrollState()
            Text(
                modifier = Modifier
                    .semantics { contentDescription = "Overview" }
                    .verticalScroll(scrollState)
                    .weight(1f), // Allow it to take available space
                text = mediaItem.overview ?: "No overview available",
                style = MaterialTheme.typography.bodySmall
            )


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    mediaItem.posterPath?.let {
                        onWatchPosterClicked(it)
                    } ?: run {
                        context.ShowToast("Poster not available")
                    }
                }, modifier = Modifier.semantics { contentDescription = "Watch Poster" }) {
                    Text(text = "Watch Poster")
                }

                Button(onClick = onWatchVideoClicked,
                    modifier = Modifier.semantics { contentDescription = "Watch Video" }) {
                    Text(text = "Watch Video")
                }
            }
        }
    }
}


