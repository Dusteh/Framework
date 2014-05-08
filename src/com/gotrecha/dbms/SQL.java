package com.gotrecha.dbms;

import com.gotrecha.util.cache.Cacheable;
import com.gotrecha.util.cache.implementations.SQLCacheable;
import com.gotrecha.util.traits.SimpleCacheTrait;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by dustin on 4/30/14.
 */
public abstract class SQL implements SQLTrait,SimpleCacheTrait,SQLCacheable{

	protected abstract String sqlStatement();
	protected abstract int getType();
	protected abstract Class getReturnType();
	protected abstract <T>T processResult(List obj);


	protected final Object[] parameters;

	protected SQL(Object... parameters) {
		this.parameters = parameters;
	}

	public <T>T execute() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		switch (getType()){
			case(QUERY):{
				return processQueryType();
			}
		}
		return null;
	}

	private <T>T processQueryType() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		//Check the cache for the given class, parameters, and query
		String cacheKey = generateCacheKey(sqlStatement(), Arrays.toString(parameters));
		Object o = getObjectFromCache(cacheKey,getCacheable());
		if(o == null) {
			o = processResult((List) executeTypedStatement(sqlStatement(), parameters, getReturnType()));
			addToCache(o, cacheKey, this);
		}
		return (T)o;
	}

	protected Cacheable getCacheable(){
		return this;
	}

}
