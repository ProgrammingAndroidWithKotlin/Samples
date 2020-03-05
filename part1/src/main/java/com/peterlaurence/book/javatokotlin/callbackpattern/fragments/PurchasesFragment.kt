package com.peterlaurence.book.javatokotlin.callbackpattern.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class PurchasesFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* Create a ViewModel the first time this Fragment is created.
         * Re-created Fragment receives the same ViewModel instance after device rotation. */
        val factory: ViewModelProvider.Factory = PurchaseViewModelFactory()
        val model =
            ViewModelProvider(this, factory).get(
                PurchasesViewModel::class.java
            )
        model.purchasesLiveData.observe(
            this,
            Observer { (_, purchases) ->
                // update UI
                println(purchases)
            }
        )
    }
}