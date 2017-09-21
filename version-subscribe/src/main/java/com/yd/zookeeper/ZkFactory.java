package com.yd.zookeeper;

import com.yd.config.Config;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import static org.apache.zookeeper.Watcher.Event.KeeperState.SyncConnected;

@SuppressWarnings("unused")
abstract class ZkFactory {

    private static final Logger logger = LoggerFactory.getLogger(ZkFactory.class);

    private static final CountDownLatch SIGNAL = new CountDownLatch(1);

    static ZooKeeper connect() throws IOException, InterruptedException {
        final int timeout = 10 * 1000;

        ZooKeeper client = new ZooKeeper(Config.ZK_HOST, timeout, event -> {
            if (event.getState() == SyncConnected) {
                SIGNAL.countDown();
                logger.debug("ZooKeeper 连接成功...");
            }
        });

        SIGNAL.await();
        return client;
    }

    static void release(ZooKeeper client) {
        Optional.ofNullable(client).ifPresent(zk -> {
            try {
                zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}