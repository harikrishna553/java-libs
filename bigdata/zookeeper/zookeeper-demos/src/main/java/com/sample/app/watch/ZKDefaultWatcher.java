package com.sample.app.watch;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ZKDefaultWatcher {

	public static void main(String[] args) throws Exception {
		ZooKeeper zk = new ZooKeeper("localhost:2181", 3000, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				System.out.println("Default Watcher triggered: " + event);
			}
		});

		// Provide a small delay to establish the connection
		Thread.sleep(1000);

		Stat stat = new Stat();

		if (zk.exists("/myNode", false) == null) {
			zk.create("/myNode", new String("Hello World").getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
					CreateMode.PERSISTENT);
		}

		// Call the methods
		byte[] data = zk.getData("/myNode", true, stat);
		System.out.println("Data: " + new String(data));

		byte[] config = zk.getConfig(null, stat);
		System.out.println("Config: " + new String(config));

		Stat nodeStat = zk.exists("/myNode", true);
		if (nodeStat != null) {
			System.out.println("/myNode exists");
		} else {
			System.out.println("/myNode does not exist");
		}

		List<String> children = zk.getChildren("/myNode", true);
		System.out.println("Children: " + children);

		// Keep the app running to catch watch events
		TimeUnit.MINUTES.sleep(5);

		zk.close();
	}
}
