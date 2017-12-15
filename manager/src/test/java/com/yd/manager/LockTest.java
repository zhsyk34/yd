package com.yd.manager;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

@SuppressWarnings("restriction")
public class LockTest {
    private Object obj = new Object();
    private int count = 0;
    private Lock lock = new Lock();

    @Test
    public void testUnsafe() {
        CountDownLatch latch = new CountDownLatch(10000);
        for (int i = 0; i < 10000; i++) {
            Worker worker = new Worker(latch);
            worker.start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public int inc() {
        synchronized (this) {
            System.out.println(count);
            return ++count;
        }
    }

    public int lockInc() {
        try {
            lock.lock();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int newCount = ++count;
        System.out.println(count);
        lock.unlock();
        return newCount;
    }

    private class Lock {
        private boolean isLocked = false;

        synchronized void lock() throws InterruptedException {
//          while(isLocked){
            if (isLocked) {
                wait();
            }
            isLocked = true;
        }

        synchronized void unlock() {
            isLocked = false;
            notify();
        }
    }

    private class Worker extends Thread {
        private CountDownLatch latch;

        Worker(CountDownLatch latch) {
            this.latch = latch;
        }

        public void run() {
            inc();
            lockInc();
            latch.countDown();
        }
    }
}
