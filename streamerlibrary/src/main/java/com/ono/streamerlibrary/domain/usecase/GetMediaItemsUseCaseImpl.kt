package com.ono.streamerlibrary.domain.usecase

import androidx.paging.PagingData
import com.ono.streamerlibrary.domain.model.MediaItem
import com.ono.streamerlibrary.domain.repository.MediaRepository
import com.ono.streamerlibrary.domain.usecase.GetMediaItemsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMediaItemsUseCaseImpl @Inject constructor(
    private val repository: MediaRepository
) : GetMediaItemsUseCase {

    override suspend operator fun invoke(query: String): Flow<PagingData<MediaItem>> =
        repository.searchMedia(query)
}