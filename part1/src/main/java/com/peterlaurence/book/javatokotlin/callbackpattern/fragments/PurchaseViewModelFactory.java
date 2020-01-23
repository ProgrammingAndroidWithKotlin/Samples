package com.peterlaurence.book.javatokotlin.callbackpattern.fragments;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.peterlaurence.book.javatokotlin.callbackpattern.core.java.PurchasesProviderImpl;
import com.peterlaurence.book.javatokotlin.callbackpattern.fragments.PurchasesViewModel.PurchasesProvider;
import com.peterlaurence.book.javatokotlin.callbackpattern.fragments.PurchasesViewModel.BillingClient;

import com.peterlaurence.book.javatokotlin.callbackpattern.core.java.BillingClientImpl;

public class PurchaseViewModelFactory implements ViewModelProvider.Factory {
    private PurchasesProvider provider = new PurchasesProviderImpl();
    private BillingClient billingClient = new BillingClientImpl(provider);

    private final String user = "user"; // Get in from registration service

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PurchasesViewModel.class)) {
            return (T) new PurchasesViewModel(billingClient, user);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
