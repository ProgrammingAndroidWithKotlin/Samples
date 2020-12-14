package com.peterlaurence.book.pawk.callbackpattern.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.peterlaurence.book.pawk.callbackpattern.core.BillingClientImpl
import com.peterlaurence.book.pawk.callbackpattern.core.PurchasesProviderImpl
import com.peterlaurence.book.pawk.callbackpattern.fragments.PurchasesViewModel.BillingClient
import com.peterlaurence.book.pawk.callbackpattern.fragments.PurchasesViewModel.PurchasesProvider

class PurchaseViewModelFactory : ViewModelProvider.Factory {
    private val provider: PurchasesProvider = PurchasesProviderImpl()
    private val billingClient: BillingClient = BillingClientImpl(provider)
    private val user = "user" // Get in from registration service

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PurchasesViewModel::class.java)) {
            return PurchasesViewModel(billingClient, user) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}