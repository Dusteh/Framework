package com.gotrecha.api;

/**
 * Created by dustin on 5/7/14.
 */
public abstract class RPCResult {
	protected final long id;
	protected final String jsonrpc = "2.0";

	protected RPCResult(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getJsonrpc() {
		return jsonrpc;
	}

	@Override
	public String toString() {
		return "RPCResult{" +
				"id=" + id +
				", jsonrpc='" + jsonrpc + '\'' +
				'}';
	}
}
