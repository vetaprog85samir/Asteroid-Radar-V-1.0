package com.egyptfwd.asteroidradar.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class PicOfTheDayModel(
    @Json(name = "media_type")
    val mediaType: String,
    val title: String,
    var url: String
    ):Parcelable