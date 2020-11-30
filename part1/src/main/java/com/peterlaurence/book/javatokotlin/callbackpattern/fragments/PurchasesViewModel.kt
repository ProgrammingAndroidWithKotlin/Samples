package com.peterlaurence.book.javatokotlin.callbackpattern.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.peterlaurence.book.javatokotlin.callbackpattern.core.UserPurchases
import com.peterlaurence.book.javatokotlin.callbackpattern.fragments.PurchasesViewModel.BillingClient.BillingCallback
import com.peterlaurence.book.javatokotlin.callbackpattern.fragments.PurchasesViewModel.PurchasesProvider.PurchaseFetchCallback

class PurchasesViewModel internal constructor(
    private val billingClient: BillingClient,
    private val user: String
) : ViewModel() {
    private var _purchases = MutableLiveData<UserPurchases>()

    private fun getUserPurchases(user: String) {
        billingClient.init { provider -> // this is called from a background thread
            provider?.fetchPurchases(user) { purchases ->
                _purchases.postValue(UserPurchases(user, purchases))
            }
        }
    }

    val purchasesLiveData: LiveData<UserPurchases>
        get() {
            getUserPurchases(user)
            return _purchases
        }

    interface BillingClient {
        fun interface BillingCallback {
            fun onInitDone(provider: PurchasesProvider?)
        }

        fun init(callback: BillingCallback)
    }

    interface PurchasesProvider {
        fun interface PurchaseFetchCallback {
            fun onPurchaseFetchDone(purchases: List<String>)
        }

        fun fetchPurchases(user: String, callback: PurchaseFetchCallback)
    }

}