package com.peterlaurence.book.javatokotlin.callbackpattern.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider

class PurchasesFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Create a ViewModel the first time this Fragment is created.
         * Re-created Fragment receives the same ViewModel instance after device rotation. */
        val factory: ViewModelProvider.Factory = PurchaseViewModelFactory()
        val model by viewModels<PurchasesViewModel> { factory }
        model.purchasesLiveData.observe(this) { (_, purchases) ->
            // update UI
            println(purchases)
        }
    }
}