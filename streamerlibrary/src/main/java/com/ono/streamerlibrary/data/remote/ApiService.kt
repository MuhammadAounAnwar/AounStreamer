package com.ono.streamerlibrary.data.remote

import com.ono.streamerlibrary.domain.model.MediaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/multi")
    suspend fun searchMedia(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MediaResponse>
}