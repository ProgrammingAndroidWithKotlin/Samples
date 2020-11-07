package com.peterlaurence.book.javatokotlin.flows.sharedflow.sharedreplay

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect

class NewsRepository(private val dao: NewsDao) {
    private val _newsFeed = MutableSharedFlow<News>(0)
    val newsFeed = _newsFeed.asSharedFlow()

    private val scope = CoroutineScope(Job() + Dispatchers.IO)

    init {
        scope.launch {
            while (true) {
                val news = dao.fetchNewsFromApi()
                scope.launch {
                    news.forEach { _newsFeed.emit(it) }
                }

                delay(3000) // wait 30s
            }
        }
    }

    fun stop() = scope.cancel()
}

interface NewsDao {
    suspend fun fetchNewsFromApi(): List<News>
}

data class News(val content: String)

class NewsViewsModel(private val repository: NewsRepository) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    init {
        viewModelScope.launch {
            repository.newsFeed.collect {
                println("NewsViewsModel receives $it")
            }
        }
    }
}

class AnotherViewModel(private val repository: NewsRepository) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    init {
        viewModelScope.launch {
            repository.newsFeed.collect {
                println("AnotherViewModel receives $it")
            }
        }
    }
}

fun main() = runBlocking {

    val dao = object : NewsDao {
        private var index = 0

        override suspend fun fetchNewsFromApi(): List<News> {
            delay(1000)
            return listOf(News("news content ${++index}"), News("news content ${++index}"))
        }
    }
    val repo = NewsRepository(dao)
    NewsViewsModel(repo)
    delay(5000)
    AnotherViewModel(repo)

    delay(30_000)
    repo.stop()
}