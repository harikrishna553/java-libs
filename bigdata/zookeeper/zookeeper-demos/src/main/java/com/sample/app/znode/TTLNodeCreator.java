package com.sample.app.znode;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class TTLNodeCreator {
	public static void main(String[] args) throws Exception {
		CountDownLatch connectedSignal = new CountDownLatch(1);

		try (ZooKeeper zk = new ZooKeeper("localhost:2181", 3000, event -> {
			if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
				connectedSignal.countDown();
			}
		})) {
			connectedSignal.await();
			// TTL in milliseconds
			long ttl = 5000l;

			String path = zk.create("/testttl", "temporary data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT_WITH_TTL, null, ttl);

			System.out.println("Created TTL Node: " + path);
			System.out.println("\nChildren of root node are:");
			List<String> children = zk.getChildren("/", false);
			for (String child : children) {
				System.out.println(child);
			}

			System.out.println("\nSleeping for 30 seconds\n");
			TimeUnit.SECONDS.sleep(30);

			System.out.println("\nChildren of root node are:");
			children = zk.getChildren("/", false);
			for (String child : children) {
				System.out.println(child);
			}
		}

	}
}
