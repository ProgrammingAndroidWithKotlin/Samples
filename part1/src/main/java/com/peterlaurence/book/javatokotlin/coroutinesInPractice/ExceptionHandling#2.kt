package com.peterlaurence.book.javatokotlin.coroutinesInPractice

import kotlinx.coroutines.*

fun main() = runBlocking {

    val scope = CoroutineScope(Job())

    val job = scope.launch {
        try {
            coroutineScope {
                val task = launch {
                    delay(1000)
                    println("Done background task")
                }

                val countDeferred = async {
                    throw Exception()
                    1
                }

                try {
                    countDeferred.await()
                } catch (e: Exception) {
                    println("Caught exception $e")
                }
                task.join()
            }
        } catch (e: Exception) {
            println("gotcha")
        }

    }

    job.join()
}