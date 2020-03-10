package com.peterlaurence.book.javatokotlin.channels.csp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.random.Random

fun main() = runBlocking<Unit> {
    val shapeChannel = Channel<Shape>()
    val shapeLocationChannel = Channel<Location>()

    with(ShapeCollector(4)) {
        start(shapeLocationChannel, shapeChannel)
        consumeShapes(shapeChannel)
    }

    sendLocations(shapeLocationChannel)
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


