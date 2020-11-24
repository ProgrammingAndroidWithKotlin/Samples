package com.peterlaurence.book.javatokotlin.flows.sharedflow.stateflow

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class ServiceStatus
object Started : ServiceStatus()
data class Downloading(val progress: Int) : ServiceStatus()
data class Aborted(val reason: String) : ServiceStatus()
object Stopped : ServiceStatus()

class DownloadService : Service() {
    companion object {
        private val _downloadState = MutableStateFlow<ServiceStatus>(Stopped)
        val downloadState = _downloadState.asStateFlow()
    }


    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onBind(intent: Intent?): IBinder? {
        // We don't provide binding, so return null
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val url = intent.getStringExtra("url")
        if (url == null) {
            stopService()
            return START_NOT_STICKY
        }

        _downloadState.value = Started
        scope.launch {
            downloadUrl(url)
            stopService()
        }
        return START_NOT_STICKY
    }

    private suspend fun downloadUrl(url: String) {
        /* Simulate a download */
        for (i in 0..100) {
            _downloadState.tryEmit(Downloading(i))
            delay(10)
        }
    }

    private fun stopService() {
        _downloadState.tryEmit(Stopped)
        stopSelf()
    }
}

class DownloadViewModel : ViewModel() {
    val downloadServiceStatus = DownloadService.downloadState.asLiveData()
}

class DownloadFragment : Fragment() {
    private val viewModel: DownloadViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.downloadServiceStatus.observe(this) {
            it?.also {
                onDownloadServiceStatus(it)
            }
        }
    }

    private fun onDownloadServiceStatus(status: ServiceStatus): Nothing = when (status) {
        Started -> TODO("Show download is a about to start")
        Stopped -> TODO("Show download stopped")
        is Downloading -> TODO("Show progress")
        is Aborted -> TODO("Something went wring")
    }
}