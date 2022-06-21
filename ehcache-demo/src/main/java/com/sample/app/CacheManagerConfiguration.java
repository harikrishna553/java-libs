package com.sample.app;

import java.util.Map;

import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class CacheManagerConfiguration {
	public static void main(String[] args) {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

		cacheManager.createCache("myCache1", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(100)).build());

		cacheManager.createCache("myCache2", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(100)).build());

		Configuration configuraiton = cacheManager.getRuntimeConfiguration();

		// Get cache configurations
		Map<String, CacheConfiguration<?, ?>> cachesConfigurations = configuraiton.getCacheConfigurations();
		for (String cacheAliasName : cachesConfigurations.keySet()) {
			System.out.println("\nConfiguraitons for the cache : " + cacheAliasName);

			CacheConfiguration<?, ?> cacheConfig = cachesConfigurations.get(cacheAliasName);
			System.out.println("key type : " + cacheConfig.getKeyType());
			System.out.println("Expiry Policy : " + cacheConfig.getExpiryPolicy());
		}

	}

}

