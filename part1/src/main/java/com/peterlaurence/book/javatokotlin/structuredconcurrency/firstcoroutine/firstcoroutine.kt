package com.peterlaurence.book.javatokotlin.structuredconcurrency.firstcoroutine

import kotlinx.coroutines.*
import kotlin.coroutines.ContinuationInterceptor

@InternalCoroutinesApi
fun main() = runBlocking {
    val job = launch {
        while (true) {
            println("I'm working")
            val x = coroutineContext[ContinuationInterceptor] as? Delay
            println(x)
            delay(10)
        }
    }

    delay(30)
    job.cancel()
}