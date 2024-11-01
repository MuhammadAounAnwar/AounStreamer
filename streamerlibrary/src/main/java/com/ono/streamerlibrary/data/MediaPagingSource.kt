package com.ono.streamerlibrary.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ono.streamerlibrary.data.remote.ApiService
import com.ono.streamerlibrary.domain.model.MediaItem

class MediaPagingSource(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, MediaItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaItem> {
        val page = params.key ?: 1

        return try {
            val response = apiService.searchMedia(query, true, "en-US", page)
            if (response.isSuccessful) {
                val mediaResponse =
                    response.body() ?: return LoadResult.Error(Exception("Response is null"))

                LoadResult.Page(
                    data = mediaResponse.mediaItems,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (page >= mediaResponse.totalPages) null else page + 1
                )
            } else {
                LoadResult.Error(Exception("Error fetching data"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MediaItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

