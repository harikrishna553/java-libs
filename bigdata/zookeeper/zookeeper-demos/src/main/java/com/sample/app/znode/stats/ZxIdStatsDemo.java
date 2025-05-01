package com.sample.app.znode.stats;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZxIdStatsDemo {
	private static final String ZK_SERVER = "localhost:2181";
	private static final int SESSION_TIMEOUT = 3000;
	private static final String PARENT_PATH = "/testNode";
	private static final String CHILD_PATH = "/testNode/child1";

	public static void main(String[] args) throws Exception {
		CountDownLatch connectedSignal = new CountDownLatch(1);

		ZooKeeper zk = new ZooKeeper(ZK_SERVER, SESSION_TIMEOUT, event -> {
			if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
				connectedSignal.countDown();
			}
		});

		connectedSignal.await();

		// Create /testNode if not exists
		if (zk.exists(PARENT_PATH, false) == null) {
			zk.create(PARENT_PATH, "initial".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("Created /testNode");
		}

		// 1. Print initial Stat
		printStat(zk, PARENT_PATH, "Stats after creation");

		// 2. Update data
		zk.setData(PARENT_PATH, "updated-data".getBytes(), -1);
		printStat(zk, PARENT_PATH, "Stats after data update");

		// 3. Create a child node
		if (zk.exists(CHILD_PATH, false) == null) {
			zk.create(CHILD_PATH, "child".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("Created /testNode/child1");
		}

		// 4. Print stat after adding child
		printStat(zk, PARENT_PATH, "Stats after adding child");

		zk.close();
	}

	private static void printStat(ZooKeeper zk, String path, String stage)
			throws KeeperException, InterruptedException {
		Stat stat = new Stat();
		byte[] data = zk.getData(path, false, stat);

		System.out.println("\n== " + stage + " ==");
		System.out.println("Data = " + new String(data));
		System.out.println("cZxid = 0x" + Long.toHexString(stat.getCzxid()));
		System.out.println("mZxid = 0x" + Long.toHexString(stat.getMzxid()));
		System.out.println("pZxid = 0x" + Long.toHexString(stat.getPzxid()));
		System.out.println("cversion = " + stat.getCversion());
		System.out.println("dataVersion = " + stat.getVersion());
		System.out.println("numChildren = " + stat.getNumChildren());
	}
}
