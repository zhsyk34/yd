package com.yd.ecabinet.util;

public abstract class ThreadUtils {

    public static void await(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
    }
}
