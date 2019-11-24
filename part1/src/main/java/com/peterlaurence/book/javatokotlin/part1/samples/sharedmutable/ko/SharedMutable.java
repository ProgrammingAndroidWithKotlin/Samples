package com.peterlaurence.book.javatokotlin.part1.samples.sharedmutable.ko;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class SharedMutable {
    private static final List<String> refCount = new ArrayList<>();

    private static class RefCounter extends Thread {
        @Override
        public void run() {
            for (int i = 0 ; i < 300 ; i++) {
                // Simulate work
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refCount.add("a ref");
            }
        }
    }

    public static void main(String[] args) {
        // Launch two workers
        new RefCounter().start();
        new RefCounter().start();

        // Then wait just enough to let the workers finish their job
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Display the size of the list - it should be 600 (300 x2)
        System.out.println(refCount.size());
    }
}
