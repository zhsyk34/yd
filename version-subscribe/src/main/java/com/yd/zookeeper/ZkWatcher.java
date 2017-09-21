package com.yd.zookeeper;

import com.yd.config.Config;
import com.yd.process.DownloadShell;
import com.yd.process.StartShell;
import com.yd.process.StopShell;
import lombok.RequiredArgsConstructor;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

import static org.apache.zookeeper.Watcher.Event.EventType.NodeDataChanged;

@RequiredArgsConstructor(staticName = "of")
public class ZkWatcher implements Watcher {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ZooKeeper client;

    @Override
    public void process(WatchedEvent event) {
        String path = event.getPath();
        Event.EventType type = event.getType();

        logger.debug("监听到{}上的{}事件", path, type);

        if (!path.equals(Config.ZK_WATCH_PATH) && type != NodeDataChanged) {
            logger.debug("只监听{}上的{}事件,当前事件不进行处理", Config.ZK_WATCH_PATH, NodeDataChanged);
            return;
        }

        try {
            byte[] data = client.getData(Config.ZK_WATCH_PATH, this, null);
            String version = new String(data, StandardCharsets.UTF_8);
            logger.info("监听到版本更新,版本号为:[{}]", version);

            this.handle(version);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handle(String version) {
        StopShell.instance().execute();

        DownloadShell.of(version).execute();

        StartShell.instance().execute();
    }
}
