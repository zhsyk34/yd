package com.yd.ecabinet.tcp;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class TcpClient {

    public static void main(String[] args) throws Exception {
        AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        Future<Void> future = client.connect(new InetSocketAddress("192.168.1.81", 1800));
        future.get();

        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(() -> {
            ByteBuffer buffer = ByteBuffer.wrap("a".getBytes(StandardCharsets.UTF_8));
            client.write(buffer);
        }, 5, 5, TimeUnit.SECONDS);

//        client.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
//            @Override
//            public void completed(Integer result, ByteBuffer attachment) {
//                if (result > 0) {
//
//                }
//            }
//
//            @Override
//            public void failed(Throwable exc, ByteBuffer attachment) {
//
//            }
//        });
//        client.read(buffer, null, new CompletionHandler<Integer, Void>() {
//            @Override
//            public void completed(Integer result, Void attachment) {
//                System.out.println("client received: " + new String(buffer.array()));
//            }
//
//            @Override
//            public void failed(Throwable exc, Void attachment) {
//                exc.printStackTrace();
//                try {
//                    client.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });

        new CountDownLatch(1).await();
    }
}