package com.sample.app.znode.stats;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class EphemeralOwnerDemo {
    private static final String ZK_SERVER = "localhost:2181";
    private static final int SESSION_TIMEOUT = 5000;

    public static void main(String[] args) throws Exception {
        // Step 1: Connect to ZooKeeper
        ZooKeeper zk = new ZooKeeper(ZK_SERVER, SESSION_TIMEOUT, event -> {});
        System.out.println("Connected to ZooKeeper with sessionId: " + Long.toHexString(zk.getSessionId()));

        // Step 2: Create a persistent node
        String persistentPath = "/java_persistent";
        if (zk.exists(persistentPath, false) == null) {
            zk.create(persistentPath, "I live forever".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        Stat stat = new Stat();
        zk.getData(persistentPath, false, stat);
        System.out.println("\n[PERSISTENT NODE]");
        printNodeStat(persistentPath, stat);

        // Step 3: Create an ephemeral node
        String ephemeralPath = "/java_ephemeral";
        if (zk.exists(ephemeralPath, false) != null) {
            zk.delete(ephemeralPath, -1);
        }

        zk.create(ephemeralPath, "short-lived".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zk.getData(ephemeralPath, false, stat);
        System.out.println("\n[EPHEMERAL NODE]");
        printNodeStat(ephemeralPath, stat);

        // Step 4: Close the ZooKeeper session
        System.out.println("\nClosing ZooKeeper session...");
        zk.close();

        // Step 5: Reconnect and check if ephemeral node exists
        Thread.sleep(2000); // Wait to ensure node gets deleted
        ZooKeeper zkReconnect = new ZooKeeper(ZK_SERVER, SESSION_TIMEOUT, event -> {});
        Stat newStat = zkReconnect.exists(ephemeralPath, false);

        System.out.println("\nAfter reconnect, ephemeral node exists? " + (newStat != null ? "Yes" : "No"));

        zkReconnect.close();
    }

    private static void printNodeStat(String path, Stat stat) {
        System.out.println("Path       : " + path);
        System.out.println("cZxid      : " + Long.toHexString(stat.getCzxid()));
        System.out.println("mZxid      : " + Long.toHexString(stat.getMzxid()));
        System.out.println("ctime      : " + new java.util.Date(stat.getCtime()));
        System.out.println("mtime      : " + new java.util.Date(stat.getMtime()));
        System.out.println("ephemeral? : " + (stat.getEphemeralOwner() != 0));
        System.out.println("ephemeralOwner : " + Long.toHexString(stat.getEphemeralOwner()));
    }
}
