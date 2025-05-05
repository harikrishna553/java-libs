package com.sample.app.util;

import java.util.Collections;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class LeaderElection implements Watcher {

	private static final String ELECTION_NAMESPACE = "/election";
	private final ZooKeeper zooKeeper;
	private String currentZnodeName;
	private String currentLeader;

	public LeaderElection(ZooKeeper zooKeeper) {
		this.zooKeeper = zooKeeper;
	}

	public void volunteerForLeadership() throws KeeperException, InterruptedException {
		String znodePrefix = ELECTION_NAMESPACE + "/c_";
		String znodeFullPath = zooKeeper.create(znodePrefix, new byte[] {}, ZooDefs.Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL_SEQUENTIAL);
		this.currentZnodeName = znodeFullPath.replace(ELECTION_NAMESPACE + "/", "");
		System.out.println("Znode assigned to this instance: " + currentZnodeName);
	}

	public void electLeader() throws KeeperException, InterruptedException {
		List<String> children = zooKeeper.getChildren(ELECTION_NAMESPACE, false);
		Collections.sort(children);
		currentLeader = children.get(0);

		if (currentLeader.equals(currentZnodeName)) {
			System.out.println("I am the leader.");
		} else {
			System.out.println("I am not the leader. Watching current leader: " + currentLeader);
			watchCurrentLeader(currentLeader);
		}
	}

	private void watchCurrentLeader(String leaderZnode) throws KeeperException, InterruptedException {
		Stat stat = zooKeeper.exists(ELECTION_NAMESPACE + "/" + leaderZnode, this);
		if (stat == null) {
			System.out.println("Leader node already deleted. Re-running election.");
			electLeader(); // Safety net
		}
	}

	public void waitUntilMinimumParticipants(int expectedCount) throws KeeperException, InterruptedException {
		while (true) {
			List<String> participants = zooKeeper.getChildren(ELECTION_NAMESPACE, false);
			if (participants.size() >= expectedCount) {
				System.out.println("Minimum participants (" + expectedCount + ") reached: " + participants);
				break;
			}
			System.out.println("Waiting for participants. Current count: " + participants.size());
			Thread.sleep(1000);
		}
	}

	@Override
	public void process(WatchedEvent event) {
		if (event.getType() == Event.EventType.NodeDeleted
				&& event.getPath().equals(ELECTION_NAMESPACE + "/" + currentLeader)) {
			try {
				System.out.println("Leader node deleted. Triggering re-election...");
				electLeader();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
