package com.sample.app.znode.stats;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

public class ZooKeeperNumChildrenDemo {
    private static final String ZK_SERVER = "localhost:2181";
    private static final int SESSION_TIMEOUT = 3000;
    private static ZooKeeper zk;
    private static CountDownLatch connectionLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        // 1. Connect to ZooKeeper
        zk = new ZooKeeper(ZK_SERVER, SESSION_TIMEOUT, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                connectionLatch.countDown();
            }
        });
        connectionLatch.await();

        String parentPath = "/myapp";

        // Cleanup if node already exists
        if (zk.exists("/myapp/service1/logs", false) != null) zk.delete("/myapp/service1/logs", -1);
        if (zk.exists("/myapp/service1", false) != null) zk.delete("/myapp/service1", -1);
        if (zk.exists("/myapp/service2", false) != null) zk.delete("/myapp/service2", -1);
        if (zk.exists(parentPath, false) != null) zk.delete(parentPath, -1);

        // 2. Create parent node
        zk.create(parentPath, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        printNumChildren(parentPath, "After creating parent");

        // 3. Create child /myapp/service1
        zk.create(parentPath + "/service1", "info".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        printNumChildren(parentPath, "After adding service1");

        // 4. Create child /myapp/service2
        zk.create(parentPath + "/service2", "info".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        printNumChildren(parentPath, "After adding service2");

        // 5. Create grandchild /myapp/service1/logs
        zk.create(parentPath + "/service1/logs", "data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        printNumChildren(parentPath, "After adding grandchild logs under service1");

        printNumChildren(parentPath + "/service1", "Children of service1");

        zk.close();
    }

    private static void printNumChildren(String path, String label) throws KeeperException, InterruptedException {
        Stat stat = zk.exists(path, false);
        if (stat != null) {
            System.out.println("[" + label + "] " + path + " has numChildren = " + stat.getNumChildren());
        } else {
            System.out.println("Node " + path + " does not exist");
        }
    }
}
