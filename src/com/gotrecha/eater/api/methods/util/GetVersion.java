package com.gotrecha.eater.api.methods.util;

import com.gotrecha.api.API;
import com.gotrecha.api.RPCRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dustin on 4/26/14.
 */
public class GetVersion extends API {

	public GetVersion(HttpServletRequest request, HttpServletResponse response, RPCRequest payload) {
		super(request, response, payload);
	}

	@Override
	public void process() {
		String version = request.getServletContext().getInitParameter("VERSION");
		String buildDate = request.getServletContext().getInitParameter("BUILD_DATE");
		try {
			writeStringAsJSON("{\"version\":\"" + version + "\",\"buildDate\":\"" + buildDate + "\"}");
		} catch (IOException e) {
			log.error("Error writing output", e);
		}
	}
}
