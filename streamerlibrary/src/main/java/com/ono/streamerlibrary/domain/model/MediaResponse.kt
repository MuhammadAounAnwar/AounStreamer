package com.ono.streamerlibrary.domain.model

import com.google.gson.annotations.SerializedName
import com.ono.streamerlibrary.domain.model.MediaItem

data class MediaResponse(
    @SerializedName("page") var page: Int = 1,
    @SerializedName("results") var mediaItems: ArrayList<MediaItem> = arrayListOf(),
    @SerializedName("total_pages") var totalPages: Int = 1,
    @SerializedName("total_results") var totalResults: Int = 0
)
