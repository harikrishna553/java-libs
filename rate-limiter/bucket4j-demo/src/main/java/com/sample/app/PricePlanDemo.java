package com.sample.app;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.sample.app.enums.PricingPlan;

import io.github.bucket4j.Bucket;

public class PricePlanDemo {

	private static final Map<String, Bucket> BUCKETS_MAP = new ConcurrentHashMap<>();

	private static void getResource(String apiKey) {
		Bucket bucket = null;

		if (BUCKETS_MAP.containsKey(apiKey)) {
			bucket = BUCKETS_MAP.get(apiKey);
		} else {
			bucket = PricingPlan.resolveBucketApiKey(apiKey);
			BUCKETS_MAP.put(apiKey, bucket);
		}

		if (bucket.tryConsume(1)) {
			System.out.println("Resource accessed....");
			return;
		}

		System.err.println("Too Many Requests, all the tokens consumed");
	}

	public static void main(String[] args) throws InterruptedException {
		while (true) {
			TimeUnit.SECONDS.sleep(1);
			getResource("FREE-12easdfgft");
		}

	}

}
