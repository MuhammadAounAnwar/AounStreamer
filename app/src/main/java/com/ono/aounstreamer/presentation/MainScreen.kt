package com.ono.aounstreamer.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.ono.aounstreamer.util.ErrorMessage
import com.ono.aounstreamer.util.LoadingNextPageItem
import com.ono.aounstreamer.MainViewModel
import com.ono.aounstreamer.util.PageLoader
import com.ono.streamerlibrary.domain.model.MediaItem
import java.util.HashMap
import java.util.Locale


@Composable
fun SearchBar(onSearch: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = {
            text = it
            onSearch(text)
        },
        label = { Text("Search") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel(), onItemSelected: (MediaItem) -> Unit) {
    var query by remember { mutableStateOf("") }
    val mediaItems = viewModel.mediaItems.collectAsLazyPagingItems()

    Column {
        SearchBar(onSearch = { query = it; viewModel.search(query) })

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            // Grouping items by media type
            val groupedMedia = mediaItems.itemSnapshotList.items
                .groupBy { it.mediaType }
                .map { (mediaType, items) -> mediaType to items }
                .sortedBy { (mediaType, _) -> mediaType }

            groupedMedia.forEach { (mediaType, items) ->
                item {
                    MediaTypeHeader(mediaType.toString())
                }
                // LazyRow for each media type
                item {
                    val lazyListState = rememberLazyListState()
                    LazyRow(
                        state = lazyListState,
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        items(items) { mediaItem ->
                            MediaCard(item = mediaItem, onItemSelected = onItemSelected)
                        }

                        // Handle loading state for the carousel
                        when (mediaItems.loadState.append) {
                            is LoadState.Loading -> {
                                item { LoadingNextPageItem(modifier = Modifier.fillParentMaxSize()) }
                            }

                            is LoadState.Error -> {
                                val error = mediaItems.loadState.append as LoadState.Error
                                item {
                                    ErrorMessage(
                                        modifier = Modifier,
                                        message = error.error.localizedMessage ?: "Unknown error",
                                        onClickRetry = { mediaItems.retry() }
                                    )
                                }
                            }

                            else -> {}
                        }
                    }

                    // Trigger pagination when end of LazyRow is reached
                    LaunchedEffect(lazyListState) {
                        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo }
                            .collect { visibleItems ->
                                val lastVisibleItem = visibleItems.lastOrNull()
                                if (lastVisibleItem != null &&
                                    lastVisibleItem.index == items.size - 1 &&
                                    mediaItems.loadState.append is LoadState.NotLoading
                                ) {
                                    mediaItems.retry() // Trigger loading the next page
                                }
                            }
                    }

                }
            }

            // Handle loading state for the entire list if needed
            if (mediaItems.loadState.refresh is LoadState.Loading) {
                item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
            }

            if (mediaItems.loadState.refresh is LoadState.Error) {
                val error = mediaItems.loadState.refresh as LoadState.Error
                item {
                    ErrorMessage(
                        modifier = Modifier.fillParentMaxSize(),
                        message = error.error.localizedMessage ?: "Unknown error",
                        onClickRetry = { mediaItems.retry() }
                    )
                }
            }

        }


    }
}

@Composable
fun MediaTypeHeader(mediaType: String) {
    Text(
        text = mediaType.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
        },
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun MediaCard(item: MediaItem, onItemSelected: (MediaItem) -> Unit) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(180.dp)
            .clickable { onItemSelected(item) },
        shape = RoundedCornerShape(8.dp),
    ) {
        Column {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${item.posterPath}",
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.title ?: "Untitled",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}
