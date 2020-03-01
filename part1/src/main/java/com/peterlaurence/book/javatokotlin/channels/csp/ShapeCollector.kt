package com.peterlaurence.book.javatokotlin.channels.csp

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.selects.select

data class Shape(val location: ShapeLocation, val data: ShapeData)
data class ShapeLocation(val x: Int, val y: Int)
class ShapeData

class ShapeCollector(private val workerCount: Int) {
    fun CoroutineScope.collectShapes(
        shapeLocations: ReceiveChannel<ShapeLocation>,
        shapesOutput: SendChannel<Shape>
    ) {
        val locationsToFetch = Channel<ShapeLocation>()
        val locationsFetched = Channel<ShapeLocation>(capacity = 1)

        repeat(workerCount) { worker(locationsToFetch, locationsFetched, shapesOutput)}
        shapeCollectorKernel(shapeLocations, locationsToFetch, locationsFetched)
    }

    private fun CoroutineScope.shapeCollectorKernel(
        shapeLocations: ReceiveChannel<ShapeLocation>,
        locationsToFetch: SendChannel<ShapeLocation>,
        locationsFetched: ReceiveChannel<ShapeLocation>
    ) = launch(Dispatchers.Default) {

        val shapesBeingProcessed = mutableListOf<ShapeLocation>()

        while (true) {
            select<Unit> {
                locationsFetched.onReceive {
                    shapesBeingProcessed.remove(it)
                }
                shapeLocations.onReceive {
                    if (!shapesBeingProcessed.any { loc -> loc == it }) {
                        /* Add it to the list of locations being processed */
                        shapesBeingProcessed.add(it)

                        /* Now download the shape at location */
                        locationsToFetch.send(it)
                    }
                }
            }
        }
    }

    private fun CoroutineScope.worker(
        locationsToFetch: ReceiveChannel<ShapeLocation>,
        locationsFetched: SendChannel<ShapeLocation>,
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

    private suspend fun getShapeData(location: ShapeLocation) = withContext(Dispatchers.IO) {
        delay(1000)
        println("Fetch location $location")
        ShapeData()
    }
}

