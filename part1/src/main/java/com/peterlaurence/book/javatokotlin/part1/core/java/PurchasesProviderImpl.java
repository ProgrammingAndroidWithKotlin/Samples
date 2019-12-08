package com.peterlaurence.book.javatokotlin.part1.core.java;

import com.peterlaurence.book.javatokotlin.part1.fragments.java.PurchasesViewModel.PurchasesProvider;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PurchasesProviderImpl implements PurchasesProvider {
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void fetchPurchases(String user, PurchaseFetchCallback callback) {
        /* perform asynchronous work */
        executor.submit(() -> {
            try {
                Thread.sleep(1000);
                callback.onPurchaseFetchDone(Arrays.asList("Purchase1", "Purchase2"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
