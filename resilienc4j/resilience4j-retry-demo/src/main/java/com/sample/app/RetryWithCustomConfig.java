package com.sample.app;

import java.time.Duration;

import com.sample.app.service.RemoteService;

import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

public class RetryWithCustomConfig {

	public static void main(String[] args) throws InterruptedException {
		RetryConfig retryConfig = RetryConfig.custom().maxAttempts(5)
				.intervalFunction(IntervalFunction.of(Duration.ofMillis(1000))).build();

		Retry retry = Retry.of("someRemoteService", retryConfig);

		RemoteService remoteService1 = new RemoteService(3);
		Runnable decoratedRunnable = Retry.decorateRunnable(retry, () -> remoteService1.accessResource());
		Thread thread1 = new Thread(decoratedRunnable);
		thread1.start();
		thread1.join();

		System.out.println("\n\n");

		RemoteService remoteService2 = new RemoteService(7);
		decoratedRunnable = Retry.decorateRunnable(retry, () -> remoteService2.accessResource());
		thread1 = new Thread(decoratedRunnable);
		thread1.start();
	}

}
