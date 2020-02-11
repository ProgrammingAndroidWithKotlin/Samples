package com.peterlaurence.book.javatokotlin.coroutinesInPractice

import kotlinx.coroutines.*
import java.io.IOException

fun main() = runBlocking {
    val ceh = CoroutineExceptionHandler { _, exception ->
        println("Caught original $exception")
    }

    val scope = CoroutineScope(coroutineContext + Job())

    val job = scope.launch(ceh) {
        launch {
            try {
                delay(Long.MAX_VALUE)
            } finally {
                println("Child 1 was cancelled")
            }
        }

        launch {
            delay(1000)
            throw IOException()
        }
    }
    job.join()
}

