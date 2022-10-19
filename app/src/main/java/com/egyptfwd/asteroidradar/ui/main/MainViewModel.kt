package com.egyptfwd.asteroidradar.ui.main

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.egyptfwd.asteroidradar.database.AsteroidEntity
import com.egyptfwd.asteroidradar.database.getAsteroidDatabase
import com.egyptfwd.asteroidradar.database.getPicOfTheDayDatabase
import com.egyptfwd.asteroidradar.repo.Repository
import kotlinx.coroutines.launch

class MainViewModel (application: Application) : AndroidViewModel(application) {

    private val picOfTheDayDb = getPicOfTheDayDatabase(application)
    private val asteroidDb = getAsteroidDatabase(application)

    private val repo = Repository(picOfTheDayDb, asteroidDb)

    val asteroidsList: MediatorLiveData<List<AsteroidEntity>> = MediatorLiveData()

    init {
        storePicOfTheDay()
        getAsteroids()
    }

    //Picture of the day
    private fun storePicOfTheDay() {

        viewModelScope.launch {
            try {
                repo.storePicOfTheDay()
            }catch (e:Exception){}
        }

    }
    val picOfTheDayEntity = repo.picOfTheDayEntity


    //Asteroids
    private fun getAsteroids() {
        viewModelScope.launch {
            try {
                repo.refreshAsteroids()
                asteroidsList.addSource(weekAsteroidList){
                    asteroidsList.value = it
                }
            }catch (e: Exception){}
        }
    }

    private val weekAsteroidList = repo.asteroidList
    private val todayAsteroidList = repo.todayAsteroidList

    fun onTodayAsteroidsClicked() {
        removeSource()
        asteroidsList.addSource(todayAsteroidList) {
            asteroidsList.value = it
        }
    }

    fun onWeekAsteroidsClicked() {
        removeSource()
        asteroidsList.addSource(weekAsteroidList) {
            asteroidsList.value = it
        }
    }

    fun onSavedAsteroidsClicked() {
        removeSource()
        asteroidsList.addSource(weekAsteroidList) {
            asteroidsList.value = it
        }
    }

    private fun removeSource() {
        asteroidsList.removeSource(todayAsteroidList)
        asteroidsList.removeSource(weekAsteroidList)
    }

    //Navigation to detail fragment
    private val _navigateToDetailFragment = MutableLiveData<AsteroidEntity?>()

    val navigateToDetailFragment: MutableLiveData<AsteroidEntity?>
        get() = _navigateToDetailFragment

    fun onAsteroidClicked (asteroid: AsteroidEntity){
        _navigateToDetailFragment.value = asteroid
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun onAsteroidNavigated (){
        _navigateToDetailFragment.value = null
    }



    /**
     */

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}