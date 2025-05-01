package com.sample.app.znode;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZooKeeperContainerNodeExample {

    private static final String ZK_SERVER = "localhost:2181";
    private static final int SESSION_TIMEOUT = 3000;

    private static final String CONTAINER_PATH = "/my-container";
    private static final String CHILD_PATH = CONTAINER_PATH + "/child1";

    public static void main(String[] args) throws Exception {
        CountDownLatch connectedSignal = new CountDownLatch(1);

        ZooKeeper zk = new ZooKeeper(ZK_SERVER, SESSION_TIMEOUT, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                connectedSignal.countDown();
            }
        });

        connectedSignal.await();

        System.out.println("Connected to ZooKeeper");

        createContainerIfNotExists(zk, CONTAINER_PATH);

        try {
            zk.create(CHILD_PATH, "child node".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("Child created: " + CHILD_PATH);
        } catch (KeeperException.NoNodeException e) {
            System.out.println("Container node was auto-deleted. Recreating...");
            createContainerIfNotExists(zk, CONTAINER_PATH);
            zk.create(CHILD_PATH, "child node".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println("Child created after recreating container: " + CHILD_PATH);
        }

        // Delete the child, -1 means delete the node regardless of its current version.
        zk.delete(CHILD_PATH, -1);
        System.out.println("Child deleted: " + CHILD_PATH);

        // Sleep to allow server to auto-delete the container node
        System.out.println("Waiting for container to be deleted...");
        Thread.sleep(10000);

        // Check if the container node still exists
        Stat containerStat = zk.exists(CONTAINER_PATH, false);
        if (containerStat == null) {
            System.out.println("Container node was auto-deleted by ZooKeeper.");
        } else {
            System.out.println("Container node still exists.");
        }

        zk.close();
        System.out.println("Disconnected from ZooKeeper");
    }

    private static void createContainerIfNotExists(ZooKeeper zk, String path) {
        try {
            Stat stat = zk.exists(path, false);
            if (stat == null) {
                zk.create(path, "container-node".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.CONTAINER);
                System.out.println("Container node created: " + path);
            } else {
                System.out.println("Container node already exists: " + path);
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
