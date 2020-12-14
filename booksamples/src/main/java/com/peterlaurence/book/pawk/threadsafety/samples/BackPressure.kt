package com.peterlaurence.book.pawk.threadsafety.samples

import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread


fun main() {
    val workQueue = LinkedBlockingQueue<Int>(5) // 5 is the size of the queue

    val producer = thread {
        while (true) {
            /* Inserts one element at the tail of the queue,
             * waiting if necessary for space to become available. */
            workQueue.put(1)
            println("Producer added a new element to the queue")
        }
    }

    val consumer = thread {
        while (true) {
            // We have slow consumer - it sleeps at each iteration
            Thread.sleep(1000)
            workQueue.take()
            println("Consumer took an element out of the queue")
        }
    }
}
