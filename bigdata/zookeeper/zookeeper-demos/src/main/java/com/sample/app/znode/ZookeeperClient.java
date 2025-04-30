package com.sample.app.znode;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZookeeperClient {
	private static final String ZK_ADDRESS = "localhost:2181";
	private static final String ELECTION_NAMESPACE = "/leader_election";
	private static final int SESSION_TIMEOUT = 3000;

	private final int clientId;
	private ZooKeeper zooKeeper;
	private String currentZnodeName;

	public ZookeeperClient(int clientId, CountDownLatch allCreated) {
		this.clientId = clientId;
	}

	public void connect() throws IOException, InterruptedException, KeeperException {
		zooKeeper = new ZooKeeper(ZK_ADDRESS, SESSION_TIMEOUT, null);
		if (zooKeeper.exists(ELECTION_NAMESPACE, false) == null) {
			synchronized (ZookeeperClient.class) {
				if (zooKeeper.exists(ELECTION_NAMESPACE, false) == null) {
					zooKeeper.create(ELECTION_NAMESPACE, new byte[] {}, ZooDefs.Ids.OPEN_ACL_UNSAFE,
							CreateMode.PERSISTENT);
				}
			}

		}

	}

	public void createElectionZnode() throws KeeperException, InterruptedException {
		String znodePrefix = ELECTION_NAMESPACE + "/leader_";
		String path = zooKeeper.create(znodePrefix, new byte[] {}, ZooDefs.Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL_SEQUENTIAL);
		this.currentZnodeName = path.replace(ELECTION_NAMESPACE + "/", "");
		System.out.println("Client " + clientId + " created znode: " + path);
	}

	public void attemptLeadership() throws KeeperException, InterruptedException {
		List<String> children = zooKeeper.getChildren(ELECTION_NAMESPACE, false);
		Collections.sort(children);

		int index = children.indexOf(currentZnodeName);
		if (index == 0) {
			System.out.println("Client " + clientId + " is the LEADER (" + currentZnodeName + ")");
		} else {
			String watchNode = children.get(index - 1);
			Stat stat = zooKeeper.exists(ELECTION_NAMESPACE + "/" + watchNode, event -> {
				if (event.getType() == Watcher.Event.EventType.NodeDeleted) {
					try {
						attemptLeadership();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			if (stat != null) {
				System.out.println("Client " + clientId + " (" + currentZnodeName + ") is watching " + watchNode);
			}
		}
	}

}