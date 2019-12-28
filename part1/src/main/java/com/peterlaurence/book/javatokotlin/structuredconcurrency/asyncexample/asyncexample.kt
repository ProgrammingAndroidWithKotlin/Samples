package com.peterlaurence.book.javatokotlin.structuredconcurrency.asyncexample

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val quick = async {
        delay(100)   // simulate some quick background work
        2
    }

    val slow = async {
        delay(1000)   // simulate some slow background work
        5
    }

    val result: Int = quick.await() + slow.await()
    println(result)
}