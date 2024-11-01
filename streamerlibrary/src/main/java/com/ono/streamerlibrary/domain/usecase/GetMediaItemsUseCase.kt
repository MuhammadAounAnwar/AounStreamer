package com.ono.streamerlibrary.domain.usecase

import androidx.paging.PagingData
import com.ono.streamerlibrary.domain.model.MediaItem
import kotlinx.coroutines.flow.Flow

interface GetMediaItemsUseCase {
    suspend operator fun invoke(query: String): Flow<PagingData<MediaItem>>
}