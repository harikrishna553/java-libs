package com.sample.app;

import java.io.File;

import org.ehcache.Cache;
import org.ehcache.PersistentCacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;

public class StoreDataToDisk {

	public static void main(String[] args) {
		PersistentCacheManager persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.with(CacheManagerBuilder.persistence(new File("/Users/Shared/ehcache")))
				.withCache("myCache",
						CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class,
								ResourcePoolsBuilder.newResourcePoolsBuilder().heap(100, EntryUnit.ENTRIES)
										.offheap(2, MemoryUnit.MB).disk(20, MemoryUnit.MB, true)))
				.build(true);

		Cache<Long, String> myCache = persistentCacheManager.getCache("myCache", Long.class, String.class);

		for (long i = 1; i < 100000; i++) {
			myCache.put(i, "data -> " + i);
		}

		persistentCacheManager.close();
	}

}
