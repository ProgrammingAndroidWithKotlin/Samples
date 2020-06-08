package com.peterlaurence.book.javatokotlin.flows.errorhandling.imperative2

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

val upstream = flowOf(1, 2, 3)
    .onEach { value ->
        if (value > 2) throw RuntimeException()
    }

fun main() = runBlocking {
    try {
        upstream.collect { value ->
            println("Received $value")
        }
    } catch (e: Throwable) {
        println("Caught $e")
    }
}