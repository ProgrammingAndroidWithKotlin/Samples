package com.peterlaurence.book.javatokotlin.threadsafety.samples.contention;

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

    // other methods which may use the intrinsic lock
}
