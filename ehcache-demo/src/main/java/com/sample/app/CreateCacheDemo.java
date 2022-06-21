package com.sample.app;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class CreateCacheDemo {

	public static void main(String[] args) {

		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

		Cache<Long, String> myCache1 = cacheManager.createCache("myCache1", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(100)).build());

		myCache1.put(11L, "Ram,45,11");
		String value1 = myCache1.get(11L);

		System.out.println("value1 : " + value1);

		cacheManager.close();

	}

}
