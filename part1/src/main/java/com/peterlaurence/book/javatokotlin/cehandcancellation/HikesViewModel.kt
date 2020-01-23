package com.peterlaurence.book.javatokotlin.cehandcancellation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class HikesViewModel : ViewModel() {
    private val hikeDataList = mutableListOf<HikeData>()
    private val hikeLiveData = MutableLiveData<List<HikeData>>()

    fun update() {
        viewModelScope.launch {
            /* Step 1: get the list of hikes */
            val hikes = hikesForUser("userId")

            /* Step 2: for each hike, get the weather, wrap into a container, update hikeDataList,
             * the notify view listeners by updating the corresponding LiveData */
            hikes.forEach { hike ->
                launch {
                    val weather = weatherForHike(hike)
                    val hikeData = HikeData(hike, weather) // container
                    hikeDataList.add(hikeData)
                    hikeLiveData.value = hikeDataList
                }
            }
        }
    }

    fun getHikeLiveData(): LiveData<List<HikeData>> {
        return hikeLiveData
    }

    private suspend fun hikesForUser(userId: String): List<Hike> = withContext(Dispatchers.IO) {
        fetchHikesForUser(userId)
    }

    private suspend fun weatherForHike(hike: Hike): Weather = withContext(Dispatchers.IO) {
        fetchWeather(hike)
    }
}