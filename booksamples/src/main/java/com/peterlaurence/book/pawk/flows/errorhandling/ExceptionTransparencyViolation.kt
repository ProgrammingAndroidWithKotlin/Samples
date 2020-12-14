package com.peterlaurence.book.pawk.flows.errorhandling.transparencyviolation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

val upstream: Flow<Int> = flow {
    for (i in 1..3) {
        try {
            emit(i)
        } catch (e: Throwable) {
            println("Intercept downstream exception $e")
        }
    }
}

fun main() = runBlocking {
    try {
        upstream.collect { value ->
            println("Received $value")
            check(value <= 2) { "Collected $value while we expect values below 2" }
        }
    } catch (e: Throwable) {
        println("Caught $e")
    }
}

val otherViolation: Flow<Int> = flowOf(1, 2, 3).handleErrors()

fun <T> Flow<T>.handleErrors(): Flow<T> = flow {
    try {
        collect { value -> emit(value) }
    } catch (e: Throwable) {
        println("error")
    }
}