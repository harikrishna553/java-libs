package com.sample.app;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.sample.app.util.LeaderElection;

@SpringBootApplication
public class App {

	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		// Start Spring Boot application
		ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
		String port = context.getEnvironment().getProperty("server.port");
		System.out.println("Started Spring Boot on port " + port);

		// Connect to ZooKeeper
		ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 3000, event -> {
		});

		// Ensure /election root node exists
		String electionRoot = "/election";
		if (zooKeeper.exists(electionRoot, false) == null) {
			zooKeeper.create(electionRoot, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}

		// Setup leader election
		LeaderElection leaderElection = new LeaderElection(zooKeeper);
		leaderElection.volunteerForLeadership();

		// Optional: Wait for a minimum number of participants before starting election
		leaderElection.waitUntilMinimumParticipants(3);

		// Start election logic
		leaderElection.electLeader();

		// Shutdown hook to cleanly close ZooKeeper connection
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				System.out.println("Shutting down...");
				zooKeeper.close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}));
	}
}
