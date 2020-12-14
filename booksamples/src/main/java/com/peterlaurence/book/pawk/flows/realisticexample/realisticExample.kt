package com.peterlaurence.book.pawk.flows.realisticexample

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private fun getDataFlow(n: Int): Flow<TokenData>{
    return flow {
        connect()
        repeat(n) {
            val token = getToken()
            val data = getData(token)
            emit(TokenData(token, data))
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

private suspend fun getData(token: String): String? {
    println("Getting data for $token")
    delay(5)
    return "data"
}

private fun disconnect() {
    println("Disconnect")
}

data class TokenData(val token: String, val data: String? = null)

fun main() = runBlocking<Unit> {
    val flow = getDataFlow(3)
    launch {
        flow.collect()
        flow.map {  }
    }
}