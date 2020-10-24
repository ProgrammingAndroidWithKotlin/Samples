package com.peterlaurence.book.javatokotlin.channels.basicsamples

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() = runBlocking {
    val channel = Channel<Item>()
    launch {
        channel.send(Item(1))
        channel.send(Item(2))
        println("Done sending")
    }

    println(channel.receive())
    println(channel.receive())

    println("Done!")
}

data class Item(val number: Int)