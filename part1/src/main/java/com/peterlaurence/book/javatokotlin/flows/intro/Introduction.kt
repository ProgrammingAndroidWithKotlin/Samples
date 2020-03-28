package com.peterlaurence.book.javatokotlin.flows.intro

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.map
import kotlinx.coroutines.channels.produce

fun CoroutineScope.numbers(): ReceiveChannel<Int> = produce {
    send(1)
    send(2)
}

suspend fun transform(n: Int) = withContext(Dispatchers.Default) {
    delay(10) // simulate some heavy CPU computations
    n + 1
}

fun main() = runBlocking {
    /* DON'T do this - uses deprecated map function on Channel */
    val channel = numbers().map {
        transform(it)
    }
    for (x in channel) {
        println(x)
    }
}