package com.peterlaurence.book.pawk.channels.basicsamples

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

fun CoroutineScope.produceValues(): ReceiveChannel<String> = produce {
    send("one")
    send("two")
}

fun main() = runBlocking {
    val receiveChannel: ReceiveChannel<String> = produceValues()

    for (e in receiveChannel) {
        println(e)
    }
}