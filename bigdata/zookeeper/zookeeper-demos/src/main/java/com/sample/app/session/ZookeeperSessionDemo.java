package com.sample.app.session;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.CountDownLatch;

public class ZookeeperSessionDemo {

	private static final String ZK_ADDRESS = "localhost:2181";
	private static final int SESSION_TIMEOUT = 3000; // 3 seconds
	private static CountDownLatch connectedLatch = new CountDownLatch(1);

	public static String toBase64(byte[] bytes) {
		if (bytes == null) {
			return "null";
		}
		return Base64.getEncoder().encodeToString(bytes);
	}

	public static void main(String[] args) throws IOException, InterruptedException {

		// Create ZooKeeper client
		ZooKeeper zooKeeper = new ZooKeeper(ZK_ADDRESS, SESSION_TIMEOUT, event -> {
			if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
				System.out.println("\n Connected to ZooKeeper!");
				connectedLatch.countDown(); // Signal connection success
			} else if (event.getState() == Watcher.Event.KeeperState.Expired) {
				System.out.println("\n Session expired! Reconnecting...");
			} else if (event.getState() == Watcher.Event.KeeperState.Closed) {
				System.out.println("\n Session closed!");
			}

		});
		
		// Wait for connection to establish
		connectedLatch.await();

		// Print session details
		System.out.println("\n=== Session Details ===");
		System.out.println("Session ID: " + zooKeeper.getSessionId());
		System.out.println("Session Password: " + toBase64(zooKeeper.getSessionPasswd()));
		System.out.println("Session Timeout: " + zooKeeper.getSessionTimeout() + "ms");

		// Keep the session alive for testing
		System.out.println("\nClosing the Session");
		zooKeeper.close();
		Thread.sleep(5000); // Simulate to receive closed event
	}
}
