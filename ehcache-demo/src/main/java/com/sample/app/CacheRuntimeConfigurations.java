package com.sample.app;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheRuntimeConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class CacheRuntimeConfigurations {
	public static void main(String[] args) {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

		Cache<Long, String> empCache = cacheManager.createCache("empCache", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(100)).build());

		CacheRuntimeConfiguration<Long, String> runTimeConfigurations = empCache.getRuntimeConfiguration();
		Class keyType = runTimeConfigurations.getKeyType();
		Class valueType = runTimeConfigurations.getValueType();

		System.out.println("keyType : " + keyType);
		System.out.println("valueType : " + valueType);

	}

}
