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

public class PersistentWatchExample {

	private static final String ZK_ADDRESS = "localhost:2181";
	private static final int SESSION_TIMEOUT = 3000;
	private static final String WATCHED_PATH = "/my-persistent-node";

	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		CountDownLatch connectedSignal = new CountDownLatch(1);

		ZooKeeper zk = new ZooKeeper(ZK_ADDRESS, SESSION_TIMEOUT, event -> {
			if (event.getState() == KeeperState.SyncConnected) {
				System.out.println("Connected to ZooKeeper");
				connectedSignal.countDown();
			}
		});

		connectedSignal.await();

		// Ensure node exists
		if (zk.exists(WATCHED_PATH, false) == null) {
			zk.create(WATCHED_PATH, "initial".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}

		// Add persistent watch
		zk.addWatch(WATCHED_PATH, new MyWatcher(), AddWatchMode.PERSISTENT);

		// Keep alive
		TimeUnit.MINUTES.sleep(5);
		zk.close();
	}

	static class MyWatcher implements Watcher {
		@Override
		public void process(WatchedEvent event) {
			if (event.getType() == EventType.NodeDataChanged) {
				System.out.println("Data changed on: " + event.getPath());
			} else if (event.getType() == EventType.NodeDeleted) {
				System.out.println("Node deleted: " + event.getPath());
			} else if (event.getType() == EventType.NodeCreated) {
				System.out.println("Node created: " + event.getPath());
			}
		}
	}
}
