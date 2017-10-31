package com.yd.log.server;

import ch.qos.logback.classic.net.server.HardenedLoggingEventInputStream;
import ch.qos.logback.classic.spi.ILoggingEvent;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

@RequiredArgsConstructor
public class LoggerHandler implements Runnable, AutoCloseable {
    private final Socket client;

    private HardenedLoggingEventInputStream stream;

    private volatile boolean closed = false;

    @Override
    public void run() {
        try {
            this.stream = new HardenedLoggingEventInputStream(this.client.getInputStream());
            while (!this.closed) {

                ILoggingEvent event = (ILoggingEvent) this.stream.readObject();
                String name = event.getLoggerName();

                Optional.ofNullable(LoggerBuilder.getLogger(name)).ifPresent(logger -> {
                    if (logger.isEnabledFor(event.getLevel())) {
                        logger.callAppenders(event);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.close();
        }
    }

    @Override
    public void close() {
        this.closed = true;
        if (this.stream != null) {
            try {
                this.stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
