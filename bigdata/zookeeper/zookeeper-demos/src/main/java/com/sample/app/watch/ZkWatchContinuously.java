package com.sample.app.watch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZkWatchContinuously {

	private static final String ZK_SERVER = "localhost:2181";
	private static final int SESSION_TIMEOUT = 3000;
	private static final String ZNODE_PATH = "/myContinuousWatchedNode";

	private static CountDownLatch connectedSignal = new CountDownLatch(1);

	// Method to register a watch repeatedly
	public static void watchNode(final ZooKeeper zk, final String path) {
		try {
			byte[] data = zk.getData(path, new Watcher() {
				public void process(WatchedEvent event) {
					System.out.println("Watch triggered: " + event);

					// Print the updated data
					try {
						byte[] newData = zk.getData(path, false, null);
						System.out.println("New data: " + new String(newData));
					} catch (Exception e) {
						e.printStackTrace();
					}

					// Re-register the watch
					watchNode(zk, path);
				}
			}, null);

			System.out.println("Initial data: " + new String(data));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

		watchNode(zk, ZNODE_PATH);

		// We can use this time to login to zkCli and update /mywatchednode
		TimeUnit.MINUTES.sleep(2);

		zk.close();
	}
}