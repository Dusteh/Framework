package com.gotrecha.util.cache.implementations;

import java.util.concurrent.TimeUnit;

/**
 * Created by dustin on 5/5/14.
 */
public interface SQLCacheable extends SimpleCacheable{

	@Override
	default TimeUnit getExpirationTimeUnit(){
		return TimeUnit.MINUTES;
	}

	@Override
	default int getExpirationTimeQuantity(){
		return 5;
	}
}
