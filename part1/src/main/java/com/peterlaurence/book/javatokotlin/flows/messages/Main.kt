package com.peterlaurence.book.javatokotlin.flows.messages

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import java.io.File
import java.net.URL
import java.time.LocalDateTime

data class Message(
    val user: String,
    val date: LocalDateTime,
    val content: String,
    val fileUrl: URL?
)

private val baseUrl = URL("http", "mysocialapp", 5210, "data/files")

fun getMessageFlow(): Flow<Message> = flow {
    emit(Message("john", LocalDateTime.now(), "I'm sending a file", URL(baseUrl, "file1")))
    emit(Message("john", LocalDateTime.now(), "I'm sending a file", URL(baseUrl, "file1")))
    emit(Message("john", LocalDateTime.now(), "I'm sending another file", URL(baseUrl, "file2")))
}

fun getMessageFlow(factory: MessageFactory) = callbackFlow<Message> {
    val observer = object : MessageFactory.MessageObserver {
        override fun onMessage(msg: Message) {
            offer(msg)
        }

        override fun onCancelled() {
            channel.close()
        }

        override fun onError(cause: Throwable) {
            cancel(CancellationException("Message factory error", cause))
        }
    }

    factory.registerObserver(observer)
    awaitClose {
        factory.unregisterObserver(observer)
    }
}

fun getMessagesFromUser(user: String): Flow<Message> {
    return getMessageFlow()
        .filter { it.user == user }
        .distinctUntilChangedBy {
            it.user + it.content + it.fileUrl
        }.flowOn(Dispatchers.Default)
}

suspend fun fetchUrl(url: URL): File = withContext(Dispatchers.IO) {
    // simulate an HTTP request delay
    delay(150)
    File(url.file)
}

fun main() = runBlocking {
    getMessagesFromUser("john").mapNotNull {
        it.fileUrl?.let { url ->  fetchUrl(url) }
    }.collect {
        println("Received ${it.name}")
    }
}