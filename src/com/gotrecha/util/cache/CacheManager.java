package com.gotrecha.util.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Created by dustin on 5/3/14.
 */
public class CacheManager {
	private static CacheManager instance;
	private final Cache<Class,Cache> masterCache;

	public static CacheManager getInstance() {
		return (instance == null)? (instance = new CacheManager()) : instance;
	}

	public CacheManager() {
		//Set up a cache of cache, this will be a cache on a class invocation, then with a cache underneath it

		masterCache = CacheBuilder.newBuilder()
				.maximumSize(250)
				.expireAfterWrite(6, TimeUnit.HOURS)
				.build();
	}

	public Cache addCacheToMasterCache(Cacheable cacheable){
		Cache cache = masterCache.getIfPresent(getClassForCacheable(cacheable));
		if(cache == null){
			cache = cacheable.buildCacheForCacheable();
			masterCache.put(cacheable.getClass(),cache);
		}
		return cache;
	}

	public Cache getCacheFromMasterCache(Cacheable cacheable){
		Cache cache = masterCache.getIfPresent(getClassForCacheable(cacheable));
		return cache;
	}

	public <T>T getValue(Object key, Cacheable cacheable){
		Cache cache = getCacheFromMasterCache(cacheable);
		if(cache != null){
			return (T)cache.getIfPresent(key);
		}
		return null;
	}

	public void setValue(Object key, Object value, Cacheable cacheable){
		Cache cache = addCacheToMasterCache(cacheable);
		cache.put(key,value);
	}

	public Class getClassForCacheable(Cacheable cacheable){
		return cacheable.getClass();
	}
}
