package com.yd.log.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LoggerServerTest {

    private static final Logger logger =
//            LoggerFactory.getLogger("[abc/def/hij]" + LoggerServerTest.class.getName());
            LoggerFactory.getLogger("[yd/test/abc]suibian");

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            logger.info("client xyz -msg:{}", LocalDateTime.now());
        }, 0, 3, TimeUnit.SECONDS);

    }
}