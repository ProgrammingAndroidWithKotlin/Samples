package com.peterlaurence.book.pawk.flows.sharedflow.eventbus

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EventBus {
    private val _startDownloadEvent = MutableSharedFlow<DownloadEvent>(
            replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val startDownloadEvent = _startDownloadEvent.asSharedFlow()

    fun startDownload(url: String) = _startDownloadEvent.tryEmit(DownloadEvent(url))
}

data class DownloadEvent(val url: String)

class Downloader(private val eventBus: EventBus, val scope: CoroutineScope) {
    init {
        scope.launch {
            println("susbscibe")
            eventBus.startDownloadEvent.collect {
                download(it.url)
            }
        }
    }

    private fun download(url: String) {
        println("Downloading $url..")
    }
}

fun main() = runBlocking {
    val eventBus = EventBus()

    delay(100)
    println("start download")
    Downloader(eventBus, this)
    eventBus.startDownload("http://somewebsite_link")
    Unit
}
