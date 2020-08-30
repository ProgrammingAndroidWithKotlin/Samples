package com.peterlaurence.book.javatokotlin.threadsafety.samples.contention

class WorkerPoolKt {
    private val workLock = Any() // In Java, we would have used `new Object()`

    fun work() {
        synchronized(workLock) {
            try {
                Thread.sleep(1000) // simulate CPU intensive task
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // other methods which may use the intrinsic lock
}