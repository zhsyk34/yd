package com.yd.log;

import com.yd.log.server.LoggerServer;

import java.util.concurrent.Executors;

public class Entry {
    public static void main(String[] args) {
        Executors.newSingleThreadExecutor().execute(new LoggerServer());
    }
}
