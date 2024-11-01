package com.ono.aounstreamer.presentation.screen

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ono.aounstreamer.MainViewModel
import com.ono.aounstreamer.presentation.components.MediaCard
import com.ono.aounstreamer.presentation.components.MediaTypeHeader
import com.ono.aounstreamer.util.ErrorMessage
import com.ono.aounstreamer.util.LoadingNextPageItem
import com.ono.aounstreamer.util.PageLoader
import com.ono.streamerlibrary.domain.model.MediaItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel(), onItemSelected: (MediaItem) -> Unit) {
    val query by viewModel._query.collectAsStateWithLifecycle()
    val mediaItems = viewModel.mediaItems.collectAsLazyPagingItems()

    Column {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            onSearch = { viewModel.search(it) },
            onQueryChange = { viewModel.search(it) },
            placeholder = { Text("Search") },
            query = query,
            active = true,
            onActiveChange = { },
            enabled = true,
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { viewModel.search("") }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear search")
                    }
                }
            },
            shape = MaterialTheme.shapes.medium,
            colors = SearchBarDefaults.colors(),
            tonalElevation = 4.dp,
            shadowElevation = 2.dp,
            windowInsets = WindowInsets(0, 0, 0, 0),
            interactionSource = remember { MutableInteractionSource() },
            content = {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    val groupedMedia = mediaItems.itemSnapshotList.items
                        .groupBy { it.mediaType }
                        .map { (mediaType, items) -> mediaType to items }
                        .sortedBy { (mediaType, _) -> mediaType }

                    groupedMedia.forEach { (mediaType, items) ->
                        item {
                            MediaTypeHeader(mediaType.toString())
                        }
                        item {
                            LazyRowForMediaType(items, onItemSelected, mediaItems)
                        }
                    }

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
        )
    }
}

@Composable
fun LazyRowForMediaType(
    items: List<MediaItem>,
    onItemSelected: (MediaItem) -> Unit,
    mediaItems: LazyPagingItems<MediaItem>
) {
    val lazyListState = rememberLazyListState()
    LazyRow(
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items) { mediaItem ->
            MediaCard(item = mediaItem, onItemSelected = onItemSelected)
        }

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
}