package com.peterlaurence.book.javatokotlin.part1.samples.listSameTime;

import org.junit.Test;

public class ATest {
    private A a = new A();

    @Test
    public void addTest() {
        Thread testThread1 = new TestThread();
        Thread testThread2 = new TestThread();

        testThread1.start();
        testThread2.start();

    }

    private class TestThread extends Thread {
        @Override
        public void run() {
            a.add();
            System.out.println(a.aList);
        }
    }
}
