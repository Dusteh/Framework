package com.gotrecha.util.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Created by dustin on 5/3/14.
 */
public interface Cacheable {
	public static final int EXPIRE_AFTER_WRITE = 0;
	public static final int EXPIRE_AFTER_ACCESS = 1;

	abstract int getExpirationType();
	abstract TimeUnit getExpirationTimeUnit();
	abstract int getExpirationTimeQuantity();
	abstract long getMaxSize();


	default public Cache buildCacheForCacheable(){
		CacheBuilder builder = CacheBuilder.newBuilder();
		if(getExpirationType() == EXPIRE_AFTER_WRITE){
			builder.expireAfterWrite(getExpirationTimeQuantity(),getExpirationTimeUnit());
		}else{
			builder.expireAfterAccess(getExpirationTimeQuantity(),getExpirationTimeUnit());
		}

		builder.maximumSize(getMaxSize());

		return builder.build();
	}

}
