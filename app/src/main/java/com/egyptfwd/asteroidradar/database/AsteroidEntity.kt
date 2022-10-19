package com.egyptfwd.asteroidradar.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.egyptfwd.asteroidradar.domain.AsteroidModel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class AsteroidEntity(
    @PrimaryKey
    val id: Long,
    @ColumnInfo
    val codename: String,
    @ColumnInfo
    val closeApproachDate: String,
    @ColumnInfo
    val absoluteMagnitude: Double,
    @ColumnInfo
    val estimatedDiameter: Double,
    @ColumnInfo
    val relativeVelocity: Double,
    @ColumnInfo
    val distanceFromEarth: Double,
    @ColumnInfo
    val isPotentiallyHazardous: Boolean
): Parcelable

fun List<AsteroidEntity>.asDomainModel(): List<AsteroidModel>{

    return map {
        AsteroidModel(
            id = it.id,
            codename= it.codename,
            closeApproachDate= it.closeApproachDate,
            absoluteMagnitude= it.absoluteMagnitude,
            estimatedDiameter= it.estimatedDiameter,
            relativeVelocity= it.relativeVelocity,
            distanceFromEarth= it.distanceFromEarth,
            isPotentiallyHazardous= it.isPotentiallyHazardous
        )
    }

}
