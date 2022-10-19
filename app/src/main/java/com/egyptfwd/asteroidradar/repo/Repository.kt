package com.egyptfwd.asteroidradar.repo

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import com.egyptfwd.asteroidradar.BuildConfig.API_KEY
import com.egyptfwd.asteroidradar.api.ApiClient
import com.egyptfwd.asteroidradar.api.asEntity
import com.egyptfwd.asteroidradar.api.parseAsteroidsJsonResult
import com.egyptfwd.asteroidradar.database.AsteroidDatabase
import com.egyptfwd.asteroidradar.database.AsteroidEntity
import com.egyptfwd.asteroidradar.database.PicOfTheDayDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class Repository(
    private val picOfTheDayDb: PicOfTheDayDatabase,
    private val asteroidDb: AsteroidDatabase
) {

    suspend fun storePicOfTheDay(){

        withContext(Dispatchers.IO){

            try {
                val response = asEntity(ApiClient.retrofitService.getPictureOfDay(API_KEY))

                picOfTheDayDb.picOfTheDayDao.updatePicOfTheDay(response)

            }catch (e: Exception){}
        }
    }

    val picOfTheDayEntity = picOfTheDayDb.picOfTheDayDao.getPicOfTheDayEntity()

    suspend fun refreshAsteroids(){

        withContext(Dispatchers.IO){
            try {
                val asteroidsList = ApiClient.retrofitService
                    .getAsteroids(API_KEY)
                val json = JSONObject(asteroidsList)
                val response = parseAsteroidsJsonResult(json)
                asteroidDb.asteroidDao.updateAsteroidsList(response)
                Timber.i("asteroid success")
            }catch (e: Exception){
                Timber.i("asteroid failure " + e.message)
            }
        }
    }

    val asteroidList: LiveData<List<AsteroidEntity>>
        get() = asteroidDb.asteroidDao.getAsteroids()


    val todayAsteroidList: LiveData<List<AsteroidEntity>>
        @SuppressLint("SimpleDateFormat")
        get() = asteroidDb.asteroidDao.getTodayAsteroids(
                SimpleDateFormat("yyyy-MM-dd").format(Date()))


}

