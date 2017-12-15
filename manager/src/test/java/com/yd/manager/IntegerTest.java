package com.yd.manager;

public class IntegerTest {

    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING = -1 << COUNT_BITS;
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    private static final int STOP = 1 << COUNT_BITS;
    private static final int TIDYING = 2 << COUNT_BITS;
    private static final int TERMINATED = 3 << COUNT_BITS;

    //低29位存线程数，高3位存runState
    public static void main(String[] args) {
        System.err.println("RUNNING:" + RUNNING);
        System.err.println("SHUTDOWN:" + SHUTDOWN);
        System.err.println("STOP:" + STOP);
        System.err.println("TIDYING:" + TIDYING);
        System.err.println("TERMINATED:" + TERMINATED);
    }
}
