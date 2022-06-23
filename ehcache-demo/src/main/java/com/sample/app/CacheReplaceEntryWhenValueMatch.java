package com.sample.app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.Cache.Entry;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class CacheReplaceEntryWhenValueMatch {

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

		Map<Long, String> dataMap = new HashMap<>();
		dataMap.put(1L, "Ram,34");
		dataMap.put(2L, "Krishna,38");
		dataMap.put(3L, "Ravi,41");
		empCache.putAll(dataMap);

		printAllElementsFromCache(empCache);

		System.out.println("\nReplace elements with keys 1 amd 5 when the value is matched to \"Ram,34\"");
		empCache.replace(1L, "Ram,34", "Rama Krishna, 41");
		empCache.replace(2L, "Ram,34", "Rama Krishna, 41");

		printAllElementsFromCache(empCache);

	}

}
