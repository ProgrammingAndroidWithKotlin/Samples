package com.peterlaurence.book.javatokotlin.cehandcancellation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HikesViewModelOld : ViewModel() {
    private val ioThreadPool: ExecutorService = Executors.newWorkStealingPool(64)
    private val hikeDataList = mutableListOf<HikeData>()
    private val hikeLiveData = MutableLiveData<List<HikeData>>()
//    private val handler = Handler(Looper.getMainLooper())

    fun fetchHikesAsync(userId: String) {
        ioThreadPool.submit {
            val hikes = fetchHikesForUser(userId)
            onHikesFetched(hikes)
        }
    }

    private fun onHikesFetched(hikes: List<Hike>) {
        hikes.forEach { hike  ->
            ioThreadPool.submit {
                val weather = fetchWeather(hike)
                val hikeData = HikeData(hike, weather) // container
                hikeDataList.add(hikeData)
                hikeLiveData.value = hikeDataList
            }
        }
    }
}