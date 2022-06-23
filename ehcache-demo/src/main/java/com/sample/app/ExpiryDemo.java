package com.sample.app;

import java.time.Duration;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.ehcache.Cache;
import org.ehcache.Cache.Entry;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.ExpiryPolicy;

import com.sample.app.dto.CachedObject;

public class ExpiryDemo {

	private static ExpiryPolicy customExpirtyPolicy = new ExpiryPolicy<Integer, CachedObject>() {

		@Override
		public Duration getExpiryForCreation(Integer key, CachedObject value) {
			return value.getExpiryTime();
		}

		@Override
		public Duration getExpiryForAccess(Integer key, Supplier<? extends CachedObject> value) {
			CachedObject cachedObject = value.get();
			return cachedObject.getExpiryTime();
		}

		@Override
		public Duration getExpiryForUpdate(Integer key, Supplier<? extends CachedObject> oldValue,
				CachedObject newValue) {
			return newValue.getExpiryTime();
		}

	};

	private static void printCacheDate(Cache<Integer, CachedObject> myCache) {
		System.out.println("\nElements in the cache are: ");

		Iterator<Entry<Integer, CachedObject>> cacheIterator = myCache.iterator();

		while (cacheIterator.hasNext()) {
			System.out.println(cacheIterator.next().getValue().getData());
		}

	}

	public static void main(String[] args) throws InterruptedException {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
		
		Cache<Integer, CachedObject> myCache = cacheManager.createCache("myCache",
				CacheConfigurationBuilder
						.newCacheConfigurationBuilder(Integer.class, CachedObject.class, ResourcePoolsBuilder.heap(100))
						.withExpiry(customExpirtyPolicy).build());

		myCache.put(1, new CachedObject("1, Krishna, 32", Duration.ofSeconds(10)));
		myCache.put(2, new CachedObject("2, Ram, 33", Duration.ofSeconds(5)));
		myCache.put(3, new CachedObject("3, Sailu, 34", Duration.ofSeconds(7)));

		printCacheDate(myCache);

		System.out.println("\nGoing to sleep for 6 seconds");
		TimeUnit.SECONDS.sleep(6);

		printCacheDate(myCache);

		System.out.println("\nGoing to sleep for 3 seconds");
		TimeUnit.SECONDS.sleep(3);

		printCacheDate(myCache);
	}

}
