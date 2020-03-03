package com.peterlaurence.book.javatokotlin.channels.csp

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.selects.select

data class Shape(val location: Location, val data: ShapeData)
data class Location(val x: Int, val y: Int)
class ShapeData

class ShapeCollector(private val workerCount: Int) {
    fun CoroutineScope.start(
        locations: ReceiveChannel<Location>,
        shapesOutput: SendChannel<Shape>
    ) {
        val locationsToFetch = Channel<Location>()
        val locationsFetched = Channel<Location>(capacity = 1)

        repeat(workerCount) { worker(locationsToFetch, locationsFetched, shapesOutput)}
        collectShapes(locations, locationsToFetch, locationsFetched)
    }

    private fun CoroutineScope.collectShapes(
        locations: ReceiveChannel<Location>,
        locationsToFetch: SendChannel<Location>,
        locationsFetched: ReceiveChannel<Location>
    ) = launch(Dispatchers.Default) {

        val locationsBeingProcessed = mutableListOf<Location>()

        while (true) {
            select<Unit> {
                locationsFetched.onReceive {
                    locationsBeingProcessed.remove(it)
                }
                locations.onReceive {
                    if (!locationsBeingProcessed.any { loc -> loc == it }) {
                        /* Add it to the list of locations being processed */
                        locationsBeingProcessed.add(it)

                        /* Now download the shape at location */
                        locationsToFetch.send(it)
                    }
                }
            }
        }
    }

    private fun CoroutineScope.worker(
        locationsToFetch: ReceiveChannel<Location>,
        locationsFetched: SendChannel<Location>,
        shapesOutput: SendChannel<Shape>
    ) = launch(Dispatchers.IO) {
        for (loc in locationsToFetch) {
            try {
                val data = getShapeData(loc)
                val shape = Shape(loc, data)
                shapesOutput.send(shape)
            } finally {
                locationsFetched.send(loc)
            }
        }
    }

    private suspend fun getShapeData(location: Location) = withContext(Dispatchers.IO) {
        delay(1000)
        println("Fetch location $location")
        ShapeData()
    }
}

