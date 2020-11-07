package com.peterlaurence.book.javatokotlin.flows.realisticexample

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private fun getDataFlow(n: Int): Flow<Data>{
    return flow {
        connect()
        repeat(n) {
            val token = getToken()
            val opt = getOpt(token)
            emit(Data(token, opt))
        }
    }.onCompletion {
        disconnect()
    }
}


private suspend fun connect() {
    println("Connecting..")
    delay(10)
}

private suspend fun getToken(): String {
    println("Getting token..")
    delay(15)
    return "token"
}

private suspend fun getOpt(token: String): String? {
    println("Getting opt for $token")
    delay(5)
    return "opt"
}

private fun disconnect() {
    println("Disconnect")
}

data class Data(val token: String, val opt: String? = null)

fun main() = runBlocking<Unit> {
    val flow = getDataFlow(3)
    launch {
        flow.collect()
        flow.map {  }
    }
}