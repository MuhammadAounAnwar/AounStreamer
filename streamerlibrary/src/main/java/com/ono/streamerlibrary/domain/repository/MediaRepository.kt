package com.ono.streamerlibrary.domain.repository

import androidx.paging.PagingData
import com.ono.streamerlibrary.domain.model.MediaItem
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    suspend fun searchMedia(query: String): Flow<PagingData<MediaItem>>
}