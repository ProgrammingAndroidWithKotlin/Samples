package com.peterlaurence.book.javatokotlin.callbackpattern.core.java;

import com.peterlaurence.book.javatokotlin.callbackpattern.fragments.PurchasesViewModel.PurchasesProvider;
import com.peterlaurence.book.javatokotlin.callbackpattern.fragments.PurchasesViewModel.BillingClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class BillingClientImpl implements BillingClient {
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private PurchasesProvider purchasesProvider;

    public BillingClientImpl(PurchasesProvider provider) {
        this.purchasesProvider = provider;
    }

    @Override
    public void init(BillingCallback callback) {
        /* perform asynchronous work here */
        executor.submit(() -> {
            try {
                Thread.sleep(1000);
                callback.onInitDone(purchasesProvider);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     *  Version using java.util.function, eliminating the need of an interface but requires
     *  API level 24
     */
    void initOther(Consumer<Boolean> f) {
        f.accept(true);
    }
}