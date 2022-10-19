package com.egyptfwd.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.egyptfwd.asteroidradar.database.getAsteroidDatabase
import com.egyptfwd.asteroidradar.database.getPicOfTheDayDatabase
import com.egyptfwd.asteroidradar.repo.Repository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }


    /**
     * A coroutine-friendly method to do your work.
     */
    override suspend fun doWork(): Result {
        val asteroidDb = getAsteroidDatabase(applicationContext)
        val picDb = getPicOfTheDayDatabase(applicationContext)
        val repo = Repository(picDb, asteroidDb)
        return try {
            repo.refreshAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}
