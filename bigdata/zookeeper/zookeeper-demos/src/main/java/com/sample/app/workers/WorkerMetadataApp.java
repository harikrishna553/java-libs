package com.sample.app.workers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class WorkerMetadataApp {

	private static final String ZOOKEEPER_SERVER = "localhost:2181"; // ZooKeeper server address
	private static final int SESSION_TIMEOUT = 3000; // Timeout in milliseconds
	private ZooKeeper zooKeeper;

	public WorkerMetadataApp() throws IOException {
		// Establish a connection to ZooKeeper server
		zooKeeper = new ZooKeeper(ZOOKEEPER_SERVER, SESSION_TIMEOUT, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				// Watcher event handler (no-op for now)
			}
		});
	}

	// Create a node with worker metadata
	public void createWorkerNode(Worker worker) throws KeeperException, InterruptedException {
		String path = "/workers/" + worker.getWorkerName();

		String workerConfig = worker.toString();
		byte[] data = workerConfig.getBytes();

		// Create the worker node under / if it doesn't exist
		if (zooKeeper.exists("/workers", false) == null) {
			zooKeeper.create("/workers", new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("/workers node created.");
		}

		// Create worker node with metadata
		zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println("Worker node created: " + path + " with config: " + workerConfig);
	}

	// Retrieve worker metadata from a node
	public String getWorkerMetadata(String workerName) throws KeeperException, InterruptedException {
		String path = "/workers/" + workerName;
		Stat stat = new Stat();
		byte[] data = zooKeeper.getData(path, false, stat);
		return new String(data);
	}

	// List all worker nodes
	public List<String> getAllWorkerNodes() throws KeeperException, InterruptedException {
		List<String> workers = zooKeeper.getChildren("/workers", false);
		return workers != null ? workers : new ArrayList<>();
	}

	public static void main(String[] args) throws Exception {
		WorkerMetadataApp app = new WorkerMetadataApp();

		List<Worker> workers = new ArrayList<>();
		workers.add(new Worker("worker1", "worker1", "192.168.1.1", "8080"));
		workers.add(new Worker("worker2", "worker2", "192.168.1.2", "8081"));
		workers.add(new Worker("worker3", "worker3", "192.168.1.3", "8082"));

		// Create worker nodes with their configurations
		for (Worker worker : workers) {
			app.createWorkerNode(worker);
		}

		// Retrieve metadata for all workers
		for (Worker worker : workers) {
			String workerMetadata = app.getWorkerMetadata(worker.getWorkerName());
			System.out.println("Worker: " + worker.getWorkerName() + " Metadata: " + workerMetadata);
		}
	}
}
