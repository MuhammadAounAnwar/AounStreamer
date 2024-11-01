package com.ono.streamerlibrary.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ono.streamerlibrary.data.MediaPagingSource
import com.ono.streamerlibrary.data.remote.ApiService
import com.ono.streamerlibrary.domain.model.MediaItem
import com.ono.streamerlibrary.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MediaRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MediaRepository {

    override suspend fun searchMedia(query: String): Flow<PagingData<MediaItem>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            pagingSourceFactory = { MediaPagingSource(apiService, query) }
        ).flow
    }
}