package com.peterlaurence.book.javatokotlin.coroutinesInPractice

import kotlinx.coroutines.*


fun main() = runBlocking {
    val job = launch(CoroutineName("wasteCpu")) {
        try {
            wasteCpu()
        } catch (e: CancellationException) {
            // handle cancellation
            e.printStackTrace()
        }
    }
    delay(200)
    job.cancelAndJoin()
    println("Done")
}

suspend fun wasteCpu() = withContext(Dispatchers.Default) {
    var nextPrintTime = System.currentTimeMillis()
    while (isActive) {
        if (System.currentTimeMillis() >= nextPrintTime) {
            println("job: I'm working..")
            nextPrintTime += 500
        }
    }
}