package com.sample.app.enums;

import java.time.Duration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

public enum PricingPlan {

	FREE(5), BASIC(400), STANDARD(1000), PREMIUM(10000);

	private int bucketCapacity;

	private PricingPlan(int bucketCapacity) {
		this.bucketCapacity = bucketCapacity;
	}

	public static Bucket resolveBucketApiKey(String apiKey) {
		if (apiKey == null || apiKey.isEmpty()) {
			Bandwidth bandwidth = Bandwidth.classic(PricingPlan.FREE.bucketCapacity,
					Refill.intervally(PricingPlan.FREE.bucketCapacity, Duration.ofHours(1)));
			return Bucket.builder().addLimit(bandwidth).build();

		}

		if (apiKey.startsWith("BASIC-")) {
			Bandwidth bandwidth = Bandwidth.classic(PricingPlan.BASIC.bucketCapacity,
					Refill.intervally(PricingPlan.BASIC.bucketCapacity, Duration.ofHours(1)));
			return Bucket.builder().addLimit(bandwidth).build();
		}

		if (apiKey.startsWith("STANDARD-")) {
			Bandwidth bandwidth = Bandwidth.classic(PricingPlan.STANDARD.bucketCapacity,
					Refill.intervally(PricingPlan.STANDARD.bucketCapacity, Duration.ofHours(1)));
			return Bucket.builder().addLimit(bandwidth).build();
		}

		if (apiKey.startsWith("PREMIUM-")) {
			Bandwidth bandwidth = Bandwidth.classic(PricingPlan.PREMIUM.bucketCapacity,
					Refill.intervally(PricingPlan.PREMIUM.bucketCapacity, Duration.ofHours(1)));
			return Bucket.builder().addLimit(bandwidth).build();
		}

		Bandwidth bandwidth = Bandwidth.classic(PricingPlan.FREE.bucketCapacity,
				Refill.intervally(PricingPlan.FREE.bucketCapacity, Duration.ofHours(1)));
		return Bucket.builder().addLimit(bandwidth).build();

	}
}
