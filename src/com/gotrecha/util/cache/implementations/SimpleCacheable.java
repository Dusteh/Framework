package com.gotrecha.util.cache.implementations;

import com.gotrecha.util.cache.Cacheable;

import java.util.concurrent.TimeUnit;

/**
 * Created by dustin on 5/5/14.
 */
public interface SimpleCacheable extends Cacheable {

	@Override
	default int getExpirationTimeQuantity(){
		return 30;
	}

	@Override
	default int getExpirationType(){
		return EXPIRE_AFTER_WRITE;
	}

	@Override
	default TimeUnit getExpirationTimeUnit(){
		return TimeUnit.MINUTES;
	}

	@Override
	default long getMaxSize(){
		return 250;
	}

}
