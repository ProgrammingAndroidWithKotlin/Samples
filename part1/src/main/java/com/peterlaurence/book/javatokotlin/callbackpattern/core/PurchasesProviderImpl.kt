package com.peterlaurence.book.javatokotlin.callbackpattern.core

import com.peterlaurence.book.javatokotlin.callbackpattern.fragments.PurchasesViewModel.PurchasesProvider
import com.peterlaurence.book.javatokotlin.callbackpattern.fragments.PurchasesViewModel.PurchasesProvider.PurchaseFetchCallback
import java.util.concurrent.Executors

class PurchasesProviderImpl : PurchasesProvider {
    private val executor =
        Executors.newSingleThreadExecutor()

    override fun fetchPurchases(
        user: String,
        callback: PurchaseFetchCallback
    ) {
        /* perform asynchronous work */
        executor.submit {
            try {
                Thread.sleep(1000)
                callback.onPurchaseFetchDone(
                    listOf("Purchase1", "Purchase2")
                )
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}