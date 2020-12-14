package com.peterlaurence.book.pawk.callbackpattern.core

import com.peterlaurence.book.pawk.callbackpattern.fragments.PurchasesViewModel.BillingClient
import com.peterlaurence.book.pawk.callbackpattern.fragments.PurchasesViewModel.BillingClient.BillingCallback
import com.peterlaurence.book.pawk.callbackpattern.fragments.PurchasesViewModel.PurchasesProvider
import java.util.concurrent.Executors
import java.util.function.Consumer

class BillingClientImpl(private val purchasesProvider: PurchasesProvider) : BillingClient {
    private val executor =
        Executors.newSingleThreadExecutor()

    override fun init(callback: BillingCallback) {
        /* perform asynchronous work here */
        executor.submit {
            try {
                Thread.sleep(1000)
                callback.onInitDone(purchasesProvider)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Version using java.util.function, eliminating the need of an interface but requires
     * API level 24
     */
    fun initOther(f: Consumer<Boolean?>) {
        f.accept(true)
    }
}