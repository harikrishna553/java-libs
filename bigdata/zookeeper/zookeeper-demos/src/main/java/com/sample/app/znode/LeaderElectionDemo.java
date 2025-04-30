package com.sample.app.znode;

import java.util.concurrent.CountDownLatch;

public class LeaderElectionDemo {

	public static void main(String[] args) throws InterruptedException {
		int numberOfClients = 5;
		CountDownLatch allCreated = new CountDownLatch(numberOfClients);

		for (int i = 1; i <= numberOfClients; i++) {
			final int id = i;
			new Thread(() -> {
				try {
					ZookeeperClient client = new ZookeeperClient(id, allCreated);
					client.connect();
					client.createElectionZnode();
					allCreated.countDown();
					allCreated.await(); // Ensure all nodes are created
					client.attemptLeadership();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
		}
	}

}
