package com.peterlaurence.book.javatokotlin.part1.fragments.java;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.peterlaurence.book.javatokotlin.part1.core.java.BillingClientImpl;
import com.peterlaurence.book.javatokotlin.part1.core.java.PurchasesProviderImpl;

public class CallbackViewModelFactory implements ViewModelProvider.Factory {
    private CallbackViewModel.BillingClient billingClient = new BillingClientImpl();
    private CallbackViewModel.PurchasesProvider purchasesProvider = new PurchasesProviderImpl();

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CallbackViewModel.class)) {
            return (T) new CallbackViewModel(billingClient, purchasesProvider);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
