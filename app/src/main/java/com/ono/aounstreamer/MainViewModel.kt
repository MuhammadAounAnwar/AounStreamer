package com.ono.aounstreamer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.flatMap
import androidx.paging.insertSeparators
import com.ono.streamerlibrary.domain.model.MediaItem
import com.ono.streamerlibrary.domain.usecase.GetMediaItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val searchMediaUseCase: GetMediaItemsUseCase
) : ViewModel() {
    private val _query = MutableStateFlow("")
    /*val mediaItems: StateFlow<PagingData<MediaItem>> =
        _query.debounce(2000).flatMapLatest { query ->
            searchMediaUseCase(query)
                .cachedIn(viewModelScope)
                .stateIn(
                    viewModelScope,
                    SharingStarted.Lazily,
                    PagingData.empty()
                )
        }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun search(query: String) {
        _query.value = query
    }*/


    private val _mediaItems = MutableStateFlow<PagingData<MediaItem>>(PagingData.empty())
    val mediaItems: StateFlow<PagingData<MediaItem>> get() = _mediaItems

    fun search(query: String) {
        viewModelScope.launch {
            searchMediaUseCase(query)
                .cachedIn(viewModelScope)
                .collectLatest { _mediaItems.value = it }
        }
    }

}
