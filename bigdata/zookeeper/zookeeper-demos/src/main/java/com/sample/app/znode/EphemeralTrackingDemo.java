package com.sample.app.znode;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class EphemeralTrackingDemo {

    private static ZooKeeper zk;
    private static CountDownLatch connectedSignal = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        zk = new ZooKeeper("localhost:2181", 3000, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                connectedSignal.countDown();
            }
        });

        connectedSignal.await();

        // Create parent znodes if they don't exist (use PERSISTENT nodes for parents)
        createIfNotExists("/services");
        createIfNotExists("/services/app1");
        createIfNotExists("/services/app2");

        // Create ephemeral nodes under different levels
        zk.create("/services/app1/ephemeralNode1", "ephemeral1".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zk.create("/services/app2/ephemeralNode2", "ephemeral2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zk.create("/ephemeralRoot", "root-level".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        // Retrieve all ephemerals created by this session
        List<String> allEphemerals = zk.getEphemerals();
        System.out.println("All Ephemeral nodes by this session:");
        allEphemerals.forEach(System.out::println);

        // Retrieve only ephemerals under /services
        List<String> serviceEphemerals = zk.getEphemerals("/services");
        System.out.println("\nEphemeral nodes under /services:");
        serviceEphemerals.forEach(System.out::println);

        zk.close();
    }

    private static void createIfNotExists(String path) throws KeeperException, InterruptedException {
        if (zk.exists(path, false) == null) {
            zk.create(path, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }
}
