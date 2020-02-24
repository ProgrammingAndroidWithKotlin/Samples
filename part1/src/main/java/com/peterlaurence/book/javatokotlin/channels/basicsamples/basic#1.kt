package com.peterlaurence.book.javatokotlin.channels.basicsamples

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() = runBlocking {
    val channel = Channel<Int>()
    launch {
        println("send 1")
        channel.send(1)
        println("send 2")
        channel.send(2)
        println("done sending")
    }

    println("receive 1")
    println(channel.receive())
    println("receive 2")
    println(channel.receive())

    println("Done!")
}