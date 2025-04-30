package com.sample.app.znode;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.WatchedEvent;

public class EphemeralNodeExample {

	public static void main(String[] args) throws Exception {
		CountDownLatch connectedSignal = new CountDownLatch(1);

		try (ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 3000, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				System.out.println("Event received: " + event);
				if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
					connectedSignal.countDown();
				}
			}
		});) {
			// Wait until the zookeeper session is established
			connectedSignal.await();

			String path = "/myEphemeralNode";
			byte[] data = "mySessionData123".getBytes();

			// Create an ephemeral node
			String createdPath = zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

			System.out.println("Ephemeral node created at: " + createdPath);

			byte[] dataBytes = zooKeeper.getData(createdPath, false, null);
			System.out.println(new String(dataBytes));

			// Keep the session alive for observation
			Thread.sleep(10000);
		}

	}
}
