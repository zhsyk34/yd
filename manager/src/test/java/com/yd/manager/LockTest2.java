package com.yd.manager;

import java.util.concurrent.locks.LockSupport;

public class LockTest2 {
    public static void main(String[] args) throws Exception {
        t2();
    }

    public static void t2() throws Exception {
        Thread t = new Thread(new Runnable() {
            private int count = 0;

            @Override
            public void run() {
                long start = System.currentTimeMillis();
                long end = 0;

                while ((end - start) <= 1000) {
                    count++;
                    end = System.currentTimeMillis();
                }

                System.out.println("after 1 second.count=" + count);

                System.out.println("thread over." + Thread.currentThread().isInterrupted());
                //等待或许许可
                LockSupport.park();
                System.out.println("thread over." + Thread.currentThread().isInterrupted());

            }
        });

        t.start();

        Thread.sleep(2000);

        // 中断线程
//        t.interrupt();
        LockSupport.unpark(t);

        System.out.println("main over");
    }
}
