package com.tomkf.nooz.network

import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable as Parcelable1

@Parcelize
data class Article(
    val title: String,
    val url: String,
    val media: List<Media>
): Parcelable1

data class ArticleResponse(
    val results: List<Article>
)

@Parcelize
data class Media(
    val type: String,
    @Json(name = "media-metadata") val mediaMetadata: List<MediaMetadata>
): Parcelable1

@Parcelize
data class MediaMetadata(
    val url: String
): Parcelable1