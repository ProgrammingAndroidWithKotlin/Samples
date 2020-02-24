package com.peterlaurence.book.javatokotlin.channels.basicsamples

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val channel = Channel<Int>()
    launch {
        channel.send(1)
        channel.send(2)
        channel.close()
    }

    for (x in channel) {
        println(x)
    }
    println("Done!")
}