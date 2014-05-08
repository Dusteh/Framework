package com.gotrecha.eater.bean.user;

import com.gotrecha.dbms.annotations.Field;

import com.gotrecha.util.cache.implementations.SimpleCacheable;

/**
 * Created by dustin on 4/26/14.
 */
public class User implements SimpleCacheable {
	@Field(sqlFieldName = "user_name")
	private final String username;
	@Field(sqlFieldName = "user_id")
	private final long id;
	@Field(sqlFieldName = "name_1")
	private final String name1;
	@Field(sqlFieldName = "name_2")
	private final String name2;
	@Field(sqlFieldName = "name_3")
	private final String name3;
	@Field(sqlFieldName = "password")
	private final String password;

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public long getId() {
		return id;
	}

	public String getName1() {
		return name1;
	}

	public String getName2() {
		return name2;
	}

	public String getName3() {
		return name3;
	}

	public User(String username, long id, String name1, String name2, String name3, String password) {

		this.username = username;
		this.id = id;
		this.name1 = name1;
		this.name2 = name2;
		this.name3 = name3;
		this.password = password;
	}

	public User() {
		this.username = null;
		this.id = -1;
		this.name1 = null;
		this.name2 = null;
		this.name3 = null;
		this.password = null;
	}
}
