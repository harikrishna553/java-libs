package com.sample.app;

import java.net.URL;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;

public class CacheViaXMLConfig {

	public static void main(String[] args) {
		URL myUrl = CacheViaXMLConfig.class.getResource("/cacheConfig.xml");
		Configuration xmlConfig = new XmlConfiguration(myUrl);
		CacheManager myCacheManager = CacheManagerBuilder.newCacheManager(xmlConfig);
		myCacheManager.init();

		Cache<Long, String> empCache1 = myCacheManager.getCache("empCache1", Long.class, String.class);
		Cache<Integer, String> empCache2 = myCacheManager.getCache("empCache2", Integer.class, String.class);
		Cache<Long, String> empCache3 = myCacheManager.getCache("empCache3", Long.class, String.class);

		empCache1.put(1L, "Krishna,34");
		empCache2.put(1, "Krishna,34");
		empCache3.put(1L, "Krishna,34");

		System.out.printf("empCache1.get(%d) : %s\n", 1L, empCache1.get(1L));
		System.out.printf("empCache2.get(%d) : %s\n", 1, empCache2.get(1));
		System.out.printf("empCache3.get(%d) : %s\n", 1L, empCache3.get(1L));

	}

}
