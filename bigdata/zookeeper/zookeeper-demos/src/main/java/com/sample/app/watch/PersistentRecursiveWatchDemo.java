package com.sample.app.watch;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.AddWatchMode;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

public class PersistentRecursiveWatchDemo {

	private static final String ZK_ADDRESS = "localhost:2181";
	private static final int SESSION_TIMEOUT = 3000;
	private static final String ROOT_PATH = "/persistent_recursive";

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		CountDownLatch connectedLatch = new CountDownLatch(1);

		ZooKeeper zk = new ZooKeeper(ZK_ADDRESS, SESSION_TIMEOUT, event -> {
			if (event.getState() == KeeperState.SyncConnected) {
				System.out.println("Connected to ZooKeeper");
				connectedLatch.countDown();
			}
		});

		connectedLatch.await();

		// Ensure root path exists
		if (zk.exists(ROOT_PATH, false) == null) {
			zk.create(ROOT_PATH, "root".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}

		// Add persistent recursive watch on /demo
		zk.addWatch(ROOT_PATH, new RecursiveWatcher(), AddWatchMode.PERSISTENT_RECURSIVE);
		
		// Sleep for 5 minutes to experiment with zkCLI
		TimeUnit.MINUTES.sleep(5);

		zk.close();
	}

	static class RecursiveWatcher implements Watcher {
		@Override
		public void process(WatchedEvent event) {
			EventType type = event.getType();
			String path = event.getPath();

			if (type == EventType.NodeCreated) {
				System.out.println("Node created: " + path);
			} else if (type == EventType.NodeDeleted) {
				System.out.println("Node deleted: " + path);
			} else if (type == EventType.NodeDataChanged) {
				System.out.println("Node data changed: " + path);
			} else {
				System.out.println("Event received: " + type + " on " + path);
			}
		}
	}
}
