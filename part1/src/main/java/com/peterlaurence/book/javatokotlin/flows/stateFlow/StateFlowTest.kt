package com.peterlaurence.book.javatokotlin.flows.stateFlow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    var tileList = listOf<Tile>(Tile(0), Tile(1), Tile(2))
    val stateFlow = MutableStateFlow(listOf<Tile>())

    launch {
        stateFlow.collect {
            println("New tile: $it")
        }
    }

    launch(Dispatchers.Default) {
        for (i in 0..5) {
            tileList = tileList.reversed()
            stateFlow.value = tileList
            delay(10)
        }
    }
    Unit
}

data class Tile(val x: Int)