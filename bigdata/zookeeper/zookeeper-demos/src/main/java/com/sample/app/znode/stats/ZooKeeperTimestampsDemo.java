package com.sample.app.znode.stats;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

public class ZooKeeperTimestampsDemo {

	private static final String ZK_SERVER = "localhost:2181";
	private static final int SESSION_TIMEOUT = 3000;
	private static ZooKeeper zk;

	public static void main(String[] args) throws Exception {
		zk = new ZooKeeper(ZK_SERVER, SESSION_TIMEOUT, event -> {
		});

		String parentPath = "/mytsnode1";
		String childPath = parentPath + "/child1";

		// Step a: Create a znode and print ctime, mtime
		if (zk.exists(parentPath, false) == null) {
			zk.create(parentPath, "initial".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("Node created: " + parentPath);
		}

		Stat stat = zk.exists(parentPath, false);
		printTimestamps("After creation", stat);

		// Step b: Update znode data
		zk.setData(parentPath, "updated-data".getBytes(), stat.getVersion());
		stat = zk.exists(parentPath, false);
		printTimestamps("After data update", stat);

		// Step c: Create a child znode
		if (zk.exists(childPath, false) == null) {
			zk.create(childPath, "child-data".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("Child node created: " + childPath);
		}
		stat = zk.exists(parentPath, false);
		printTimestamps("After child creation", stat);

		// Step d: Set ACL on parent node
		List<ACL> readOnlyAcl = Collections.singletonList(new ACL(ZooDefs.Perms.READ, ZooDefs.Ids.ANYONE_ID_UNSAFE));
		zk.setACL(parentPath, readOnlyAcl, stat.getAversion());
		stat = zk.exists(parentPath, false);
		printTimestamps("After setting ACL", stat);

		zk.close();
	}

	private static void printTimestamps(String label, Stat stat) {
		System.out.println("\n=== " + label + " ===");
		System.out.println("ctime: " + Instant.ofEpochMilli(stat.getCtime()));
		System.out.println("mtime: " + Instant.ofEpochMilli(stat.getMtime()));
		System.out.println("cversion (children changes): " + stat.getCversion());
		System.out.println("dataVersion: " + stat.getVersion());
		System.out.println("aclVersion: " + stat.getAversion());
	}
}
