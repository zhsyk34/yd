package com.yd.log.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ServerSocketFactory;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.yd.log.config.LoggerConfig.SERVER_PORT;

public class LoggerServer implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void run() {
        try (ServerSocket server = ServerSocketFactory.getDefault().createServerSocket(SERVER_PORT)) {
            while (!Thread.currentThread().isInterrupted()) {
                Socket client = server.accept();
                logger.info("accept client:{}", client);
                executorService.submit(new LoggerHandler(client));
            }
        } catch (Exception e) {
            logger.error("error", e);
        }
    }
}
