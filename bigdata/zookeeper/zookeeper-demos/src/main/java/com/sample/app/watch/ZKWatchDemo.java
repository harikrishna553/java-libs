package com.sample.app.watch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZKWatchDemo {

	private static final String ZK_SERVER = "localhost:2181";
	private static final int SESSION_TIMEOUT = 3000;
	private static final String ZNODE_PATH = "/mywatchednode";

	private static CountDownLatch connectedSignal = new CountDownLatch(1);

	public static void main(String[] args) throws Exception {

		ZooKeeper zk = new ZooKeeper(ZK_SERVER, SESSION_TIMEOUT, new Watcher() {
			public void process(WatchedEvent event) {
				// Triggered on initial connection
				if (event.getState() == Event.KeeperState.SyncConnected) {
					connectedSignal.countDown();
				}
			}
		});

		connectedSignal.await();

		// Ensure the node exists
		Stat stat = zk.exists(ZNODE_PATH, false);
		if (stat == null) {
			zk.create(ZNODE_PATH, "InitialData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}

		// Set a watch when reading the data
		byte[] data = zk.getData(ZNODE_PATH, new Watcher() {
			public void process(WatchedEvent event) {
				System.out.println("Watch triggered: " + event);
				try {
					byte[] newData = zk.getData(ZNODE_PATH, false, null);
					System.out.println("New data: " + new String(newData));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, null);

		System.out.println("Initial data: " + new String(data));

		// We can use this time to login to zkCli and update /mywatchednode
		TimeUnit.MINUTES.sleep(2);

		zk.close();
	}
}

