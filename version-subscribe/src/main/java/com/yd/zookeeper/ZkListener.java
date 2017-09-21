package com.yd.zookeeper;

import com.yd.config.Config;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZkListener {

    private static ZooKeeper client;

    private static final Logger logger = LoggerFactory.getLogger(ZkListener.class);

    public static void main(String[] args) {
        try {
            client = ZkFactory.connect();
        } catch (IOException | InterruptedException e) {
            logger.error("ZooKeeper 连接失败", e);
            return;
        }

        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(() -> {
            try {
                client.exists(Config.ZK_WATCH_PATH, ZkWatcher.of(client));

                new CountDownLatch(1).await();
            } catch (KeeperException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.shutdown();
    }

}
