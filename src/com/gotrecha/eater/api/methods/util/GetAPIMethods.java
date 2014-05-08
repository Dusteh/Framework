package com.gotrecha.eater.api.methods.util;

import com.google.gson.*;
import com.gotrecha.api.API;
import com.gotrecha.api.MethodEnumeration;
import com.gotrecha.api.RPCRequest;

import javax.servlet.http.*;
import java.io.*;

/**
 * Created by dustin on 4/26/14.
 */
public class GetAPIMethods extends API {

	public GetAPIMethods(HttpServletRequest request, HttpServletResponse response, RPCRequest payload) {
		super(request, response, payload);
	}

	@Override
	public void process() {
		MethodEnumeration[] methods = MethodEnumeration.values();
		JsonObject responseObj = new JsonObject();
		for(MethodEnumeration method:methods){
			responseObj.addProperty(method.name(),method.getMethodUUID());
		}

		try {
			writeResult(responseObj);
		} catch (IOException e) {
			log.error("Failed to write JSON object to response",e);
		}

	}
}
