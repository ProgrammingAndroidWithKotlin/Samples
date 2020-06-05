package com.peterlaurence.book.javatokotlin.flows.usecase2

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class Location
class Content

suspend fun transform(loc: Location): Content = withContext(Dispatchers.IO) {
    println("transform")
    Content()
}

val locationsFlow = flowOf(Location(), Location())

fun main() = runBlocking {
    // Defining the Flow of Content - nothing is executing yet
    val contentFlow = locationsFlow.map { loc ->
        flow {
            emit(transform(loc))
        }
    }.flattenMerge(4)

    // We now collect the entire flow using the toList terminal operator
    val contents = contentFlow.toList()
}