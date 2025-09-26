package com.sample.app.threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class LoadBalancerDemo {
	private final List<String> servers;
	private final AtomicInteger currentServer = new AtomicInteger(0);

	public LoadBalancerDemo(List<String> servers) {
		this.servers = new ArrayList<>(servers); // defensive copy
	}

	public String getServerForRequest(String request) {
		int index = currentServer.getAndUpdate(i -> (i + 1) % servers.size());
		return servers.get(index);
	}

	public static void main(String[] args) {
		List<String> serverList = Arrays.asList("Server-1", "Server-2", "Server-3");
		LoadBalancerDemo loadBalancer = new LoadBalancerDemo(serverList);

		// Simulate multiple user requests (multi-threaded)
		ExecutorService executor = Executors.newFixedThreadPool(5);

		for (int i = 1; i <= 10; i++) {
			final int requestId = i;
			executor.submit(() -> {
				String request = "UserRequest-" + requestId;
				String server = loadBalancer.getServerForRequest(request);
				System.out.println(
						request + " handled by " + server + " (Thread: " + Thread.currentThread().getName() + ")");
			});
		}

		executor.shutdown();
	}
}
