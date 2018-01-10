package com.yd.ecabinet.tcp;

import com.yd.ecabinet.config.TcpConfig;
import com.yd.rfid.RfidOperator;
import com.yd.rfid.executor.AbstractDaemonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;

//TODO
@Service
@RequiredArgsConstructor
@Slf4j
public class TcpServer extends AbstractDaemonService {
    private static final String ANY_ADDRESS = "0.0.0.0";

    private final TcpConfig tcpConfig;
    private final ExecutorService service;
    private final RfidOperator rfidOperator;

    private AsynchronousSocketChannel client;

    @Override
    public void run() {
        try {
            AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(AsynchronousChannelGroup.withThreadPool(service)).bind(new InetSocketAddress(ANY_ADDRESS, tcpConfig.getPort()));

            server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
                @Override
                public void completed(AsynchronousSocketChannel channel, Void attachment) {
                    server.accept(attachment, this);
                    logger.info("client {} connect", channel);

                    TcpServer.this.close(client);
                    TcpServer.this.client = channel;

                    TcpServer.this.handle();
                }

                @Override
                public void failed(Throwable exc, Void attachment) {
                    logger.error("server accept client error", exc);
                }
            });

            logger.info("TCP服务已启动完毕");
            super.setFinished(true);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void close(AsynchronousSocketChannel client) {
        try {
            if (client != null) {
                client.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void send(String msg) {
        if (this.client == null) {
            logger.error("当前无客户端连接");
        } else {
            client.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
        }
    }

    private void handle() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1 << 8);
        buffer.clear();

        client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if (result == -1) {
                    TcpServer.this.close(client);
                    return;
                }

                if (result == 0) {
                    return;
                }

                attachment.flip();

                try {
                    CharBuffer c = StandardCharsets.UTF_8.newDecoder().decode(attachment);
                    logger.info("接收到Python端请求:{}", c);
                    rfidOperator.openAndClose();//TODO
                } catch (CharacterCodingException e) {
                    logger.error(e.getMessage(), e);
                }
                attachment.clear();
                client.read(buffer, buffer, this);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                logger.error("receive msg error", exc);
            }
        });
    }
}
