package com.peterlaurence.book.pawk.flows.errorhandling.declarative2

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

val upstream = flowOf(1, 3, -1)

val encapsulateError = upstream
    .onEach {
        if (it < 0) throw NumberFormatException("Values should be greater than 0")
    }
    .catch { e ->
        println("Caught $e")
    }

fun main() = runBlocking {
    try {
        encapsulateError.collect {
            if (it > 2) throw RuntimeException()
            println("Received $it")
        }
    } catch (e: RuntimeException) {
        println("Collector stopped collecting the flow")
    }
}