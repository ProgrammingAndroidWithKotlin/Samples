package com.peterlaurence.book.javatokotlin.flows.firstflow

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun numbers(): Flow<Int> = flow {
    emit(1)
    emit(2)
    // emit other values
}

@InternalCoroutinesApi
fun main() = runBlocking {
    val flow = numbers()
    flow.collect {
        println(it)
    }
}