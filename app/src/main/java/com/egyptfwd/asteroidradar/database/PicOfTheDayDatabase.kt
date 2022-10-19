package com.egyptfwd.asteroidradar.database


import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [PicOfTheDayEntity::class], version = 1)
abstract class PicOfTheDayDatabase : RoomDatabase() {

    abstract val picOfTheDayDao: PicOfTheDayDao

}