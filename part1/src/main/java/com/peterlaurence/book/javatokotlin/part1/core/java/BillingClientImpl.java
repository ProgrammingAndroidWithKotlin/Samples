package com.peterlaurence.book.javatokotlin.part1.core.java;

import com.peterlaurence.book.javatokotlin.part1.fragments.java.CallbackViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class BillingClientImpl implements CallbackViewModel.BillingClient {
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void init(BillingCallback callback) {
        /* perform asynchronous work here */
        executor.submit(() -> {
            try {
                Thread.sleep(1000);
                callback.onInitDone(true);
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