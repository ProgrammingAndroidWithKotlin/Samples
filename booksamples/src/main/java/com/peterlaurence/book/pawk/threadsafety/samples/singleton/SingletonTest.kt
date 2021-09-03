package com.peterlaurence.book.pawk.threadsafety.samples.singleton

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

object RetryRefreshToken {
    var retryCount = AtomicInteger(0)

//    fun increment() {
//        retryCount++
//    }
}

fun main() = runBlocking {
    val job1 = launch(Dispatchers.IO) {
        repeat(100000) {
            RetryRefreshToken.retryCount.incrementAndGet()
//            synchronized(RetryRefreshToken) {
//                RetryRefreshToken.increment()
//            }
        }
    }
    val job2 = launch(Dispatchers.IO) {
        repeat(100000) {
            RetryRefreshToken.retryCount.incrementAndGet()
//            RetryRefreshToken.increment()
//            synchronized(RetryRefreshToken) {
//                RetryRefreshToken.increment()
//            }
        }
    }

    job1.join()
    job2.join()
    println(RetryRefreshToken.retryCount)
}