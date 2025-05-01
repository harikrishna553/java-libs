package com.sample.app.znode.stats;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZnodeDataLengthDemo {

    private static final String ZK_ADDRESS = "localhost:2181";
    private static final int SESSION_TIMEOUT = 5000;
    private static final String ZNODE_PATH = "/unicodeDemo";

    public static void main(String[] args) throws Exception {
        CountDownLatch connectedSignal = new CountDownLatch(1);

        ZooKeeper zk = new ZooKeeper(ZK_ADDRESS, SESSION_TIMEOUT, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                connectedSignal.countDown();
            }
        });

        connectedSignal.await();

        if (zk.exists(ZNODE_PATH, false) != null) {
            zk.delete(ZNODE_PATH, -1); // Cleanup before demo
        }

        // Step 1: ASCII data
        String asciiData = "hello";
        createOrUpdateZnode(zk, asciiData);

        // Step 2: Chinese characters
        String chineseData = "你好"; // 3 bytes per char = 6 bytes
        createOrUpdateZnode(zk, chineseData);

        // Step 3: Emoji
        String emojiData = "🚀"; // 4 bytes
        createOrUpdateZnode(zk, emojiData);

        zk.close();
    }

    private static void createOrUpdateZnode(ZooKeeper zk, String data) throws Exception {
        byte[] byteData = data.getBytes(StandardCharsets.UTF_8);
        Stat stat;

        if (zk.exists(ZNODE_PATH, false) == null) {
            zk.create(ZNODE_PATH, byteData, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("Created znode with data: " + data);
        } else {
            zk.setData(ZNODE_PATH, byteData, -1);
            System.out.println("Updated znode with data: " + data);
        }

        stat = zk.exists(ZNODE_PATH, false);
        System.out.println("Human-readable data: " + data);
        System.out.println("UTF-8 byte length: " + byteData.length);
        System.out.println("ZooKeeper dataLength: " + stat.getDataLength());
        System.out.println("---------------------------------------------");
    }
}
