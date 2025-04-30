package com.sample.app.znode;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZNodeCreator {
	private static final String ZK_SERVER = "localhost:2181";
	private static final int SESSION_TIMEOUT = 3000;

	public static void main(String[] args) throws Exception {
		CountDownLatch connectedSignal = new CountDownLatch(1);

		ZooKeeper zk = new ZooKeeper(ZK_SERVER, SESSION_TIMEOUT, event -> {
			if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
				connectedSignal.countDown();
			}
		});

		connectedSignal.await();

		String path = "/myAppConfig";
		String data = "env=prod;version=1.0.0";

		// Create a persistent znode with data
		if (zk.exists(path, false) == null) {
			zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("ZNode created at path: " + path);
		} else {
			System.out.println("ZNode already exists at path: " + path);
		}

		// Read and print the data
		byte[] retrievedData = zk.getData(path, false, null);
		System.out.println("ZNode Data: " + new String(retrievedData));

		// Get version info
		Stat stat = new Stat();
		zk.getData(path, false, stat);
		System.out.println("Data version: " + stat.getVersion());

		zk.close();
	}
}