package com.peterlaurence.book.pawk.coroutinesInPractice

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class HikesViewModelGood : ViewModel() {
    private val ioThreadPool: ExecutorService = Executors.newWorkStealingPool(64)
    private val hikeDataList = mutableListOf<HikeData>()
    private val hikeLiveData = MutableLiveData<List<HikeData>>()
    private val handler: Handler = Handler(Looper.getMainLooper())

    fun fetchHikesAsync(userId: String) {
        ioThreadPool.submit {
            val hikes = fetchHikesForUser(userId)
            onHikesFetched(hikes)
        }
    }

    private fun onHikesFetched(hikes: List<Hike>) {
        hikes.forEach { hike ->
            ioThreadPool.submit {
                val weather = fetchWeather(hike)
                val hikeData = HikeData(hike, weather) // container
                handler.post {
                    hikeDataList.add(hikeData)
                    hikeLiveData.value = hikeDataList
                }
            }
        }
    }

    fun addHike(hike: Hike) {
        hikeDataList.add(HikeData(hike, null))
        // then fetch Weather and notify view using hikeLiveData
    }
}