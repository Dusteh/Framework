package com.gotrecha.api;

import com.google.gson.*;

/**
 * Created by dustin on 4/26/14.
 * Java representation of the API payload object
 * JSON comes in the form of:
 * {
 * 	"id": (Unique Identifier, form of a long),
 * 	"method": (Name of call you wish to make, form of a long, pulled from method enumeration),
 * 	"params": [(Data for method call)]
 *
 * }
 */
public class RPCRequest {
	private final long id;
	private final long method;
	private final JsonObject params;
	private final String jsonrpc = "2.0";

	public String getJsonrpc() {
		return jsonrpc;
	}

	public RPCRequest(long id, long method, JsonObject params) {
		this.id = id;
		this.method = method;
		this.params = params;
	}


	public long getId() {
		return id;
	}

	public long getMethod() {
		return method;
	}

	public JsonObject getParams() {
		return params;
	}

	@Override
	public String toString() {
		return "Payload{" +
				"params=" + params +
				", id=" + id +
				", method=" + method +
				'}';
	}
}
