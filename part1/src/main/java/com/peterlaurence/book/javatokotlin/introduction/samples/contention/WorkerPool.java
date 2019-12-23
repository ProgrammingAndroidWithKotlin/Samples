package com.peterlaurence.book.javatokotlin.introduction.samples.contention;

public class WorkerPool {
    private final Object workLock = new Object();

    public void work() {
        synchronized (workLock) {
            try {
                Thread.sleep(1000); // simulate CPU intensive task
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // other methods
}
