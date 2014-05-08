package com.gotrecha.api;

/**
 * Created by dustin on 5/3/14.
 */
public enum MessageCodeEnumeration {
	NORMAL(0),
	FAILED_VALIDATION(100);

	private final long value;

	MessageCodeEnumeration(long value) {
		this.value = value;
	}

	public long getValue() {
		return value;
	}
}
