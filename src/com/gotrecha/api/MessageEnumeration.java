package com.gotrecha.api;

/**
 * Created by dustin on 5/3/14.
 */
public enum MessageEnumeration  {
	ERROR(400),
	MESSAGE(200);

	private final long value;

	MessageEnumeration(long value) {
		this.value = value;
	}

	public long getValue() {
		return value;
	}
}
