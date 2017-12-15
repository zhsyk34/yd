package com.yd.log.server;

import ch.qos.logback.classic.net.server.HardenedLoggingEventInputStream;
import ch.qos.logback.classic.spi.ILoggingEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.Optional;

@RequiredArgsConstructor
public class LoggerHandler implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Socket client;

    @Override
    public void run() {
        try (HardenedLoggingEventInputStream stream = new HardenedLoggingEventInputStream(this.client.getInputStream())) {
            while (!Thread.currentThread().isInterrupted()) {
                ILoggingEvent remote = (ILoggingEvent) stream.readObject();

                Optional.ofNullable(LoggerBuilder.build(remote.getLoggerName())).ifPresent(local -> {
//                    logger.error("local logger:{},{}", local.getName(), local.getLevel());
//                    logger.error("remote logger:{}", remote.getLevel());
//                    if(remote.getLevel())
                    local.callAppenders(remote);
//                    if (local.isEnabledFor(remote.getLevel())) {
//                    }
                });
            }
        } catch (Exception e) {
            logger.error("error", e);
        }
    }

}
