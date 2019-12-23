package com.peterlaurence.book.javatokotlin.structuredconcurrency.exceptionhandling

import kotlinx.coroutines.*

fun main() = runBlocking {
    val ceh = CoroutineExceptionHandler { _, e ->
        println("Handled $e")
    }
    val scope = CoroutineScope(ceh + Dispatchers.IO)

    scope.launch {
        val results = allOrNothing()
            .mapNotNull {
                try {
                    it.await()
                } catch (_: Throwable) {
                    null
                }
            }

        // do something with the result
        println(results)
    }

    delay(1000)
}

class UnexpectedException : Throwable("Unexpected")

suspend fun allOrNothing(): List<Deferred<String>> = coroutineScope {
    val one = async {
        delay(10)
        throw UnexpectedException()
    }

    val two = async {
        delay(100)
        println("Task two finishes")
        "Two"
    }
    listOf(one, two)
}


suspend fun allOrSome(): List<Deferred<String>> = supervisorScope {
    val one = async {
        delay(10)
        throw UnexpectedException()
    }

    val two = async {
        delay(10)
        println("Task two finishes")
        "Two"
    }
    listOf(one, two)
}
