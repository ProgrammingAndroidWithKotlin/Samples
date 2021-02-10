package com.peterlaurence.book.pawk.flows.customOperators

import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.selects.whileSelect

/**
 * Buffers the upstream flow producing lists of elements when:
 * * A number of [maxSize] elements have been emitted
 * * A timeout of [maxDelayMillis] has expired
 *
 * Consequently, the produced lists of elements have a maximum size of [maxSize].
 */
fun <T> Flow<T>.bufferTimeout(maxSize: Int, maxDelayMillis: Long): Flow<List<T>> = flow {
    require(maxSize > 0) { "maxSize should be greater than 0" }
    require(maxDelayMillis > 0) { "maxDelayMillis should be greater than 0" }

    coroutineScope {
        val channel = produceIn(this)
        val ticker = ticker(maxDelayMillis)
        val buffer = mutableListOf<T>()

        suspend fun emitBuffer() {
            if (buffer.isNotEmpty()) {
                emit(buffer.toList())
                buffer.clear()
            }
        }

        try {
            whileSelect {
                channel.onReceive { value ->
                    buffer.add(value)
                    if (buffer.size >= maxSize) emitBuffer()
                    true
                }
                ticker.onReceive {
                    emitBuffer()
                    true
                }
            }
        } catch (e: ClosedReceiveChannelException) {
            emitBuffer()
        } finally {
            channel.cancel()
            ticker.cancel()
        }
    }
}

suspend fun main() {
    val flow = (1..100).asFlow().onEach { delay(10) }
    val startTime = System.currentTimeMillis()
    flow.bufferTimeout(10, 50).collect {
        val time = System.currentTimeMillis() - startTime
        println("$time ms: $it")
    }
}