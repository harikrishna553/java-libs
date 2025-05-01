package com.sample.app.znode.stats;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;

public class ZookeeperVersionDemo {
	private static final String ZK_ADDRESS = "localhost:2181";
	private static final int SESSION_TIMEOUT = 3000;

	public static void main(String[] args) throws Exception {
		CountDownLatch connectedSignal = new CountDownLatch(1);

		ZooKeeper zk = new ZooKeeper(ZK_ADDRESS, SESSION_TIMEOUT, event -> {
			if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
				connectedSignal.countDown();
			}
		});

		connectedSignal.await();
		System.out.println("Connected to ZooKeeper");

		String parentPath = "/myVersionNode";
		
		// Cleanup if exists
		if (zk.exists(parentPath, false) != null) {
			zk.delete(parentPath + "/child1", -1);
			zk.delete(parentPath, -1);
		}

		// 1. Create node
		zk.create(parentPath, "data1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println("\nCreated node:");
		printStat(zk, parentPath);

		// 2. Update data
		zk.setData(parentPath, "data2".getBytes(), -1);
		System.out.println("\nAfter data update:");
		printStat(zk, parentPath);

		// 3. Add child
		zk.create(parentPath + "/child1", "child-data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println("\nAfter adding child:");
		printStat(zk, parentPath);

		// 4. Change ACL
		ACL readOnlyAcl = new ACL(ZooDefs.Perms.READ, new Id("world", "anyone"));
		List<ACL> aclList = Collections.singletonList(readOnlyAcl);
		zk.setACL(parentPath, aclList, -1);
		System.out.println("\nAfter setting ACL:");
		printStat(zk, parentPath);

		zk.close();
	}

	private static void printStat(ZooKeeper zk, String path) throws Exception {
		Stat stat = new Stat();
		zk.getData(path, false, stat);

		System.out.println("Path: " + path);
		System.out.println("Data version (dataVersion): " + stat.getVersion());
		System.out.println("Children version (cversion): " + stat.getCversion());
		System.out.println("ACL version (aclVersion): " + stat.getAversion());
		System.out.println("czxid: " + stat.getCzxid());
		System.out.println("mzxid: " + stat.getMzxid());
		System.out.println("pzxid: " + stat.getPzxid());
		System.out.println("numChildren: " + stat.getNumChildren());
	}
}
