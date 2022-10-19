package com.egyptfwd.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM AsteroidEntity ORDER BY date(closeApproachDate) ASC")
    fun getAsteroids(): LiveData<List<AsteroidEntity>>


    @Query("SELECT * FROM AsteroidEntity WHERE closeApproachDate <=:date ORDER BY date(closeApproachDate) ASC ")
    fun getTodayAsteroids(date: String): LiveData<List<AsteroidEntity>>

    @Transaction
    fun updateAsteroidsList(asteroidsList: List<AsteroidEntity>): List<Long> {
        deleteAllAsteroids()
        return insertAllAsteroid(asteroidsList)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroid(asteroids: List<AsteroidEntity>): List<Long>

    @Query("DELETE FROM AsteroidEntity")
    fun deleteAllAsteroids()

}

private lateinit var INSTANCE: AsteroidDatabase

fun getAsteroidDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AsteroidDatabase::class.java,
                "asteroid_db").build()
        }
    }
    return INSTANCE
}