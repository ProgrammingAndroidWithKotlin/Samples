package com.peterlaurence.book.javatokotlin.flows.errorhandling.imperative

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

val upstream = flowOf(1, 2, 3)

fun main() = runBlocking {
    try {
        upstream.collect { value ->
            if (value > 2) {
                throw RuntimeException()
            }
            println("Received $value")
        }
    } catch (e: Throwable) {
        println("Caught $e")
    }
}

