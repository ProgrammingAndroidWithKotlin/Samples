package com.peterlaurence.book.pawk.channels.csp

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
        val locationsToProcess = Channel<Location>()
        val locationsProcessed = Channel<Location>(capacity = 1)

        repeat(workerCount) { worker(locationsToProcess, locationsProcessed, shapesOutput)}
        collectShapes(locations, locationsToProcess, locationsProcessed)
    }

    private fun CoroutineScope.collectShapes(
        locations: ReceiveChannel<Location>,
        locationsToProcess: SendChannel<Location>,
        locationsProcessed: ReceiveChannel<Location>
    ) = launch(Dispatchers.Default) {

        val locationsBeingProcessed = mutableListOf<Location>()

        while (true) {
            select<Unit> {
                locationsProcessed.onReceive {
                    locationsBeingProcessed.remove(it)
                }
                locations.onReceive {
                    if (!locationsBeingProcessed.any {
                                loc -> loc == it }) {
                        /* Add it to the list of locations being processed */
                        locationsBeingProcessed.add(it)

                        /* Now download the shape at location */
                        locationsToProcess.send(it)
                    }
                }
            }
        }
    }

    private fun CoroutineScope.worker(
        locationsToProcess: ReceiveChannel<Location>,
        locationsProcessed: SendChannel<Location>,
        shapesOutput: SendChannel<Shape>
    ) = launch(Dispatchers.IO) {
        for (loc in locationsToProcess) {
            try {
                val data = getShapeData(loc)
                val shape = Shape(loc, data)
                shapesOutput.send(shape)
            } finally {
                locationsProcessed.send(loc)
            }
        }
    }

    private suspend fun getShapeData(location: Location) = withContext(Dispatchers.IO) {
        /* Simulate some remote API delay */
        delay(500)
        ShapeData()
    }
}

