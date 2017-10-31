package com.yd.log.server;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.yd.log.config.Config.PORT;

public class LoggerServer implements Runnable, AutoCloseable {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private volatile boolean closed;
    private ServerSocket server;

    @Override
    public void run() {
        try {
            this.server = ServerSocketFactory.getDefault().createServerSocket(PORT);

            while (!this.closed) {
                Socket socket = this.server.accept();
                this.executorService.submit(new LoggerHandler(socket));
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
        if (this.server != null) {
            try {
                this.server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
