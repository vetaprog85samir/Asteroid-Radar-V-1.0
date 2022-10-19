package com.egyptfwd.asteroidradar.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
data class PicOfTheDayEntity (
    val mediaType: String,
    val title: String,
    @PrimaryKey
    val url: String
    ): Parcelable

































