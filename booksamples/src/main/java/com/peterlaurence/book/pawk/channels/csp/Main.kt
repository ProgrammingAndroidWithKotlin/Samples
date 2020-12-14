package com.peterlaurence.book.pawk.channels.csp

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlin.random.Random

fun main() = runBlocking<Unit> {
    val shapes = Channel<Shape>()
    val locations = Channel<Location>()

    with(ShapeCollector(4)) {
        start(locations, shapes)
        consumeShapes(shapes)
    }

    sendLocations(locations)
}

var count = 0

fun CoroutineScope.consumeShapes(shapesInput: ReceiveChannel<Shape>) = launch {
    for (shape in shapesInput) {
        // increment a counter of shapes
        count++
    }
}

fun CoroutineScope.sendLocations(locationsOutput: SendChannel<Location>) = launch {
    withTimeoutOrNull(3000) {
        while (true) {
            /* Simulate fetching some shape location */
            val location = Location(Random.nextInt(), Random.nextInt())
            locationsOutput.send(location)
        }
    }
    println("Received $count shapes")
}


