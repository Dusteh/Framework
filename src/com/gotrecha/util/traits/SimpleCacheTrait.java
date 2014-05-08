package com.gotrecha.util.traits;

import com.gotrecha.util.cache.CacheManager;
import com.gotrecha.util.cache.Cacheable;

import java.util.Arrays;

/**
 * Created by dustin on 5/3/14.
 */
public interface SimpleCacheTrait {

	default String generateCacheKey(String... keyComponents){
		return Arrays.toString(keyComponents);
	}

	default <T>T getObjectFromCache(String cacheKey,Cacheable cacheable){
		return CacheManager.getInstance().getValue(cacheKey,cacheable);
	}

	default void addToCache(Object result,String cacheKey,Cacheable cacheable){
		CacheManager.getInstance().setValue(cacheKey,result,cacheable);
	}

}
