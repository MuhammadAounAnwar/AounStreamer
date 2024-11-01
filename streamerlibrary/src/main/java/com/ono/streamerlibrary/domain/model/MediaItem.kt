package com.ono.streamerlibrary.domain.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class MediaItem(
    @SerializedName("backdrop_path") var backdropPath: String? = null,
    @SerializedName("id") var id: Int = 0,
    @SerializedName("title") var title: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("media_type") var mediaType: String? = null,
    @SerializedName("adult") var adult: Boolean? = null,
    @SerializedName("original_language") var originalLanguage: String? = null,
    @SerializedName("genre_ids") var genreIds: ArrayList<Int> = arrayListOf(),
    @SerializedName("popularity") var popularity: Double? = null,
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("video") var video: Boolean? = null,
    @SerializedName("vote_average") var voteAverage: Double? = null,
    @SerializedName("vote_count") var voteCount: Int? = null

)


fun MediaItem.toJsonObject(): String {
    return Gson().toJson(this)
}

fun String.toMediaItem(): MediaItem? {
    return try {
        Gson().fromJson(this, MediaItem::class.java)
    } catch (e: Exception) {
        e.printStackTrace() // Handle the exception as needed
        println("Error parsing JSON: $this") // Print the problematic JSON
        null
    }
}