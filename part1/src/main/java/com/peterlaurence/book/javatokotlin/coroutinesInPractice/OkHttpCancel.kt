package com.peterlaurence.book.javatokotlin.coroutinesInPractice

import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

fun main() = runBlocking {
    val job = launch {
        val response = performHttpRequest()
        println("Got response ${response?.bytes()?.size}")
    }
    delay(200)
    job.cancelAndJoin()
    println("Done")
}

val okHttpClient = OkHttpClient()
val request = Request.Builder().url("http://localhost:3200/mapview-tile/0/0/0").build()

suspend fun performHttpRequest(): ResponseBody? = withContext(Dispatchers.IO) {
    val call = okHttpClient.newCall(request)
    call.await()
}

suspend fun Call.await() = suspendCancellableCoroutine<ResponseBody?> { continuation ->
    continuation.invokeOnCancellation {
        cancel()
    }

    enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            continuation.resume(response.body)
        }

        override fun onFailure(call: Call, e: IOException) {
            continuation.resumeWithException(e)
        }
    })
}

