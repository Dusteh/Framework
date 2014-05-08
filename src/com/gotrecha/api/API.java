package com.gotrecha.api;

import com.google.gson.*;
import com.gotrecha.util.traits.*;
import org.apache.logging.log4j.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * Created by dustin on 4/26/14.
 */
public abstract class API implements UtilTrait,APIExtension {
	protected final HttpServletRequest request;
	protected final HttpServletResponse response;
	protected final RPCRequest payload;
	protected final Logger log = log();




	protected API(HttpServletRequest request, HttpServletResponse response, RPCRequest payload) {
		this.request = request;
		this.response = response;
		this.payload = payload;
	}

	public abstract void process();

	protected void writeError(String message,MessageCodeEnumeration errorCode) throws IOException{
		RPCResult error = prepareErrorMessage(payload.getId(), message, errorCode);
		log.trace("Write Error Message: " + error.toString());
//		writeStringAsJSON(RPCResultObject.toJsonString());
		writeJSON(error);
	}



	protected void writeResult(Object data) throws IOException{
		RPCResult result = prepareMessage(payload.getId(), data);
		log.trace("Result: "+result);
		writeJSON(result);
	}

	protected void writeJSON(RPCResult data) throws IOException{
		Gson gson = getGson();
		writeStringAsJSON(gson.toJson(data));
	}

	protected void write(String data) throws IOException{
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(data);
			writer.flush();
		}finally {
			writer.close();
		}
	}

	protected void writeStringAsJSON(String data) throws IOException{
		response.setContentType("application/json");
		write(data);
	}

	protected void returnPageAsJson(String path) throws IOException,ServletException {
		response.setContentType("application/json");
		request.getRequestDispatcher(path).forward(request,response);
	}

	protected Cookie getCookie(String name){
		if(request != null){
			Cookie[] cookies = request.getCookies();
			for(Cookie cookie: cookies){
				if(cookie.getName().equals(name)){
					return cookie;
				}
			}
		}
		return null;
	}

	protected void setCookie(String name, String value){
		Cookie cookie = new Cookie(name,value);
		setCookie(cookie);
	}

	protected void setCookie(Cookie cookie){
		response.addCookie(cookie);
	}


}
