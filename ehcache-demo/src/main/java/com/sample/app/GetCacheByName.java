package com.sample.app;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class GetCacheByName {

	public static void main(String[] args) {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

		cacheManager.createCache("empCache", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(100)).build());

		Cache<Long, String> empCache = cacheManager.getCache("empCache", Long.class, String.class);

		empCache.put(1L, "Krishna");
		empCache.put(2L, "Ram");

		Set<Long> empids = new HashSet<>();
		empids.add(1L);
		empids.add(2L);
		empids.add(3L);

		Map<Long, String> empsInfo = empCache.getAll(empids);

		for (Long key : empsInfo.keySet()) {
			System.out.println(key + " -> " + empsInfo.get(key));
		}

		cacheManager.close();

	}

}
