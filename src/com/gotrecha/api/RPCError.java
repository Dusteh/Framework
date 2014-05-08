package com.gotrecha.api;

/**
 * Created by dustin on 5/7/14.
 */
public class RPCError {
	private final long code;
	private final String message;
	private final Object data;

	public long getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public RPCError(long code, String message, Object data) {

		this.code = code;
		this.message = message;
		this.data = data;
	}
}
