package com.sample.app;

import java.util.Iterator;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.Cache.Entry;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class CachePutIfAbsentDemo {

	private static void printAllElementsFromCache(Cache<Long, String> empCache) {
		System.out.println("\nPrint all the elements from the cache");

		Iterator<Entry<Long, String>> iter = empCache.iterator();

		while (iter.hasNext()) {
			Entry<Long, String> entry = iter.next();
			System.out.println(entry.getKey() + " is mapped to (" + entry.getValue() + ")");
		}

	}

	public static void main(String[] args) {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

		Cache<Long, String> empCache = cacheManager.createCache("empCache", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(100)).build());

		empCache.put(1L, "Ram");
		empCache.put(2L, "Krishna");

		printAllElementsFromCache(empCache);

		System.out.println("\nInsert entries with keys 1 and 3 to the cache");

		String val1 = empCache.putIfAbsent(1L, "Rama Krishna");
		String val2 = empCache.putIfAbsent(3L, "Joel");
		
		System.out.println("val1: " + val1);
		System.out.println("val2: " + val2);

		printAllElementsFromCache(empCache);

	}

}
