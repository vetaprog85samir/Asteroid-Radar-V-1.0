package com.egyptfwd.asteroidradar.database


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PicOfTheDayDao {

    @Query("SELECT * FROM picofthedayentity")
    fun getPicOfTheDayEntity(): LiveData<PicOfTheDayEntity>

    @Transaction
    fun updatePicOfTheDay(pic: PicOfTheDayEntity): Long {
        deleteAllPicOfTheDayEntities()
        return insertPicOfTheDayEntity(pic)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPicOfTheDayEntity(picOfTheDayEntity: PicOfTheDayEntity): Long

    @Query("DELETE  FROM picofthedayentity")
    fun deleteAllPicOfTheDayEntities()
}

private lateinit var INSTANCE: PicOfTheDayDatabase

fun getPicOfTheDayDatabase(context: Context): PicOfTheDayDatabase {
    synchronized(PicOfTheDayDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                PicOfTheDayDatabase::class.java,
                "pic_of_the_day_db").build()
        }
    }
    return INSTANCE
}