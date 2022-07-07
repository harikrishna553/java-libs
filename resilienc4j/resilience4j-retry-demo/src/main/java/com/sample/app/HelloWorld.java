package com.sample.app;

import com.sample.app.service.RemoteService;

import io.github.resilience4j.retry.Retry;

public class HelloWorld {

	public static void main(String[] args) throws InterruptedException {
		// Above snippet create a Retry with at most 3 retries and a fixed time interval
		// of 500ms between retries
		Retry retry = Retry.ofDefaults("someRemoteService");

		RemoteService remoteService1 = new RemoteService(3);
		Runnable decoratedRunnable = Retry.decorateRunnable(retry, () -> remoteService1.accessResource());
		Thread thread1 = new Thread(decoratedRunnable);
		thread1.start();
		thread1.join();

		System.out.println("\n\n");

		RemoteService remoteService2 = new RemoteService(4);
		decoratedRunnable = Retry.decorateRunnable(retry, () -> remoteService2.accessResource());
		thread1 = new Thread(decoratedRunnable);
		thread1.start();

	}

}
