package com.peterlaurence.book.pawk.flows.errorhandling

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

val upstream = flowOf(1, 2, 3)

val encapsulateError = upstream
    .onEach {
        if (it > 2) throw RuntimeException()
    }
    .catch { e ->
        println("Caught $e")
    }

fun main() = runBlocking {
    encapsulateError.collect {
        println("Received $it")
    }
}