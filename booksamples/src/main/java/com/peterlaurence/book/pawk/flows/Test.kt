package com.peterlaurence.book.pawk.flows

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

private fun zipFlow() = callbackFlow {
    val progressionListener = Task.ProgressionListener { p ->
        if (offer(p)) println("Emitted $p")
    }

    val zipTask = Task(progressionListener)
    launch {
        zipTask.start()
    }

    awaitClose {
        println("autoclose")
        zipTask.cancel()
    }
}

class Task(
    private val listener: ProgressionListener
) {
    @Volatile
    private var isCancelled = false

    suspend fun start() {
        for (i in 1..10000) {
            if (isCancelled) break
            delay(500)
            listener.onZipProgress(i)
        }
    }

    fun cancel() {
        isCancelled = true
    }

    fun interface ProgressionListener {
        fun onZipProgress(p: Int)
    }
}

fun main() = runBlocking<Unit> {
    val scope = CoroutineScope(Dispatchers.Default)
    val flow: Flow<Int> = zipFlow()
        .shareIn(scope, SharingStarted.WhileSubscribed())
        .conflate()

    suspend fun slowCollection() {
        flow.collect {
            println("Slow collector receives $it")
            delay(1000)
        }
    }

    val j = launch {
        slowCollection()
    }
    launch {
        delay(6000)
        j.cancel()
        slowCollection()
    }

}