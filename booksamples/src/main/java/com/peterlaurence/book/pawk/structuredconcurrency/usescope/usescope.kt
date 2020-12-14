package com.peterlaurence.book.pawk.structuredconcurrency.usescope

import kotlinx.coroutines.*

//fun main() = runBlocking<Unit> {
//    launch(Dispatchers.IO) {
//        coroutineScope {
//            println("scope 1")
//            delay(2000)
//        }
//
//        coroutineScope {
//            println("scope 2")
//        }
//    }
////    val scope = CoroutineScope(Job() + CoroutineName("scope"))
//    launch(CoroutineName("my-launch")) {
//        val threadName = Thread.currentThread().name
//        println("I'm executing in $threadName")
//        delay(100)  // simulate blocking IO
//    }
//
////    scope.coroutineContext[Job]?.join()
//}


fun main() = runBlocking<Unit> {
    launch {
        while (true) {
            delay(10)
            println("I'm working")
        }
        fetchAndLoadProfile("")
    }
    val c = coroutineContext[Job]?.isCompleted
    println("I've finished working and I'm completed: $c")
    println("finished $c")
}


val scope = CoroutineScope(Job())
class Profile

fun fetchAndLoadProfile(id: String) {
    scope.launch {
        val profile = fetchProfile(id)
        loadProfile(profile)
    }
}

suspend fun fetchProfile(id: String): Profile  = withContext(Dispatchers.Default){
    delay(10)
    val p = Profile()
    p
}

fun loadProfile(profile: Profile) {
    SomeClass("bob", 3)
}

class SomeClass() {
    constructor(name:String) : this() {
        println("KT name=$name")
    }

    constructor(name:String, value: Int) : this(name) {
        println("KT name=$name")
    }
}


//fun main() = runBlocking<Unit>(Dispatchers.Main) {
//    launch(Dispatchers.Default) {
//        val threadName = Thread.currentThread().name
//        println("I'm executing in $threadName")
//    }
//}
