package com.peterlaurence.book.javatokotlin.flows.sharedflow.unbuffered

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val sharedFlow = MutableSharedFlow<String>()

    launch { // First subscriber
        sharedFlow.collect {
            println("Subscriber 1 receives $it")
        }
    }

    launch { // Second subscriber - slow
        sharedFlow.collect {
            println("Subscriber 2 receives $it")
            delay(3000)
        }
    }

    launch { // Start emitting values
        sharedFlow.emit("one")
        sharedFlow.emit("two")
        sharedFlow.emit("three")
    }

    Unit
}
