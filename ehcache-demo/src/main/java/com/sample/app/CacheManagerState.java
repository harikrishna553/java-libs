package com.sample.app;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;

public class CacheManagerState {

	public static void main(String[] args) {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(false);

		System.out.println("CacheManager status : " + cacheManager.getStatus());

		System.out.println("\nInitializing CacheManager");
		cacheManager.init();
		System.out.println("CacheManager status : " + cacheManager.getStatus());

		System.out.println("\nClosing CacheManager");
		cacheManager.close();
		System.out.println("CacheManager status : " + cacheManager.getStatus());
	}

}
