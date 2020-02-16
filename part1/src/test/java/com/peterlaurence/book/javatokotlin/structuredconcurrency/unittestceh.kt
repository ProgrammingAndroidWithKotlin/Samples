package com.peterlaurence.book.javatokotlin.structuredconcurrency

import com.peterlaurence.book.javatokotlin.MyTestGloblalCEH
import com.peterlaurence.book.javatokotlin.testTrowable
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.*
import org.junit.Assert.assertNotNull
import org.junit.Test

class GlobalCEHTest {
    private val scope = CoroutineScope(Job())

    @Test
    fun example() = runBlocking {
        val job = scope.launch {
            coroutineScope {
                launch {
                    throw Exception()
                }
            }
        }

        job.join()

        /**
         * Since we didn't register a CEH, we're expecting that the global handler to handle the
         * uncaught exception. This is what we're testing.
         * To be able to do so, we have registered a custom [MyTestGloblalCEH] to the list of services
         * for kotlinx.coroutines.CoroutineExceptionHandler.
         * When [MyTestGloblalCEH] handles a throwable, it updates [testTrowable] with this throwable.
         * This isn't thread safe at all - we assume that tests relying on this are run serially.
         */
        assertNotNull(testTrowable)
        assertNull(testTrowable) // check cleanup
        println("Program ends")
    }
}