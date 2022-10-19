package com.egyptfwd.asteroidradar.api


import com.egyptfwd.asteroidradar.database.PicOfTheDayEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PicOfTheDayDto(
    @Json(name = "media_type")
    val mediaType: String,
    val title: String,
    val url: String
)


fun asEntity(picOfTheDayDto: PicOfTheDayDto): PicOfTheDayEntity {

    return with(picOfTheDayDto) {
        PicOfTheDayEntity(
            url = this.url,
            mediaType = this.mediaType,
            title = this.title
        )
    }

}