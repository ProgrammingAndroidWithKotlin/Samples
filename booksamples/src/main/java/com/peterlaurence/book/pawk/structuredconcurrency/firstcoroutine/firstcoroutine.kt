package com.peterlaurence.book.pawk.structuredconcurrency.firstcoroutine

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch {
        while (true) {
            println("I'm working")
            delay(10)
        }
    }

    delay(30)
    job.cancel()
}