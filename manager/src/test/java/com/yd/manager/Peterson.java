package com.yd.manager;

public class Peterson {
    private volatile boolean[] flag = new boolean[2];//希望进入临界区的进程号
    private volatile int turn = -1;//有权访问的进程号

    public static void main(String[] args) {
        Peterson peterson = new Peterson();
        new Thread(() -> {
            while (true) {
                peterson.processOne();
            }
        }).start();
        new Thread(() -> {
            while (true) {
                peterson.processTwo();
            }
        }).start();
    }

    private void processOne() {
        flag[0] = true;
        turn = 1;//礼让?
        while (flag[1] && turn == 1) {

        }
        System.out.println("processOne");
        flag[0] = false;
    }

    private void processTwo() {
        flag[1] = true;
        turn = 0;
        while (flag[0] && turn == 0) {

        }
        System.out.println("processTwo");
        flag[1] = false;
    }

    //A1 A2 B C D
    //A/A1 A2
}
