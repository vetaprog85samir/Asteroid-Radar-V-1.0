package com.egyptfwd.asteroidradar.api

import com.egyptfwd.asteroidradar.database.AsteroidEntity
import com.egyptfwd.asteroidradar.domain.AsteroidModel
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class AsteroidDtoContainer(val asteroidDto: ArrayList<AsteroidDto>)

@JsonClass(generateAdapter = true)
data class AsteroidDto(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

fun AsteroidDtoContainer.asDomainModel(): List<AsteroidModel> {

    return asteroidDto.map {
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

fun AsteroidDtoContainer.asEntity(): List<AsteroidEntity>{

    return asteroidDto.map {
        AsteroidEntity(
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
