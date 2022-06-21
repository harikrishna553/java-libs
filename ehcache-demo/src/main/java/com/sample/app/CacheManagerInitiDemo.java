package com.sample.app;

import org.ehcache.CacheManager;
import org.ehcache.Status;
import org.ehcache.config.builders.CacheManagerBuilder;

public class CacheManagerInitiDemo {

	public static void main(String[] args) {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(false);

		Status status = cacheManager.getStatus();
		System.out.println("CacheManager status is set to " + status);

		System.out.println("Initializing CacheManager");
		cacheManager.init();
		
		status = cacheManager.getStatus();
		System.out.println("CacheManager status is set to " + status);
		
		cacheManager.close();

	}

}
