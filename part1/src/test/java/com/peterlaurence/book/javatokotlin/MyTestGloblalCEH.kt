package com.peterlaurence.book.javatokotlin

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

var testTrowable: Throwable? = null
    // every get sets the value null
    get() {
        val formerValue = field
        field = null
        return formerValue
    }
    private set

class MyTestGloblalCEH : AbstractCoroutineContextElement(CoroutineExceptionHandler),
    CoroutineExceptionHandler {

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        println("Catch $exception from MyCEH")
        testTrowable = exception
    }
}