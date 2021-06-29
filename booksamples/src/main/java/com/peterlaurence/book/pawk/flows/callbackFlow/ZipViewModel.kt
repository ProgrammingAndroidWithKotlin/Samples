package com.peterlaurence.book.pawk.flows.callbackFlow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.io.OutputStream

class ZipViewModel : ViewModel() {
    private val _zipEvents = MutableLiveData<ZipEvent>()
    val zipEvents: LiveData<ZipEvent> = _zipEvents

    fun zipContent(content: Content, outputStream: OutputStream) {
        val flow = zipFlow(content, outputStream)
        viewModelScope.launch {
            flow.distinctUntilChanged().collect {
                _zipEvents.value = it
            }
        }
    }

    private fun zipFlow(
        content: Content,
        outputStream: OutputStream
    ): Flow<ZipEvent> = callbackFlow {

        val progressionListener = object : ZipTask.ZipProgressionListener {
            override fun onZipProgress(p: Int) {
                trySend(ZipProgressEvent(p))
            }

            override fun onZipFinished() {
                trySendBlocking(ZipFinishedEvent)
                trySendBlocking(ZipCloseEvent)
                channel.close()
            }

            override fun onZipError(e: Exception) {
                trySendBlocking(ZipErrorEvent(e))
                trySendBlocking(ZipCloseEvent)
                cancel(CancellationException("Error in ZipTask", e))
            }
        }

        val zipTask = ZipTask(progressionListener, content, outputStream)
        zipTask.start()

        /* Suspend until the task is either completed or cancelled */
        awaitClose {
            zipTask.cancel()
        }
    }
}

sealed class ZipEvent
data class ZipProgressEvent(val progress: Int) : ZipEvent()
object ZipFinishedEvent : ZipEvent()
data class ZipErrorEvent(val e: Exception) : ZipEvent()
object ZipCloseEvent : ZipEvent()

class Content