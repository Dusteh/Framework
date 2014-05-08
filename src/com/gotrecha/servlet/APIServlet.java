package com.gotrecha.servlet;

import com.google.gson.*;
import com.gotrecha.api.MethodEnumeration;
import com.gotrecha.api.RPCRequest;
import org.apache.logging.log4j.*;

import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.lang.reflect.*;

/**
 * Created by dustin on 4/26/14.
 */
@WebServlet(name="APIServlet",urlPatterns ={"/api"})
public class APIServlet extends BaseServlet {
	private final Logger log = log();
	private final Gson gson = getGson();

	@Override
	protected void process(HttpServletRequest request, HttpServletResponse response) {
		log.trace("API Called");
		String payloadStr = request.getParameter(Constants.PAYLOAD_PARAMETER);
		RPCRequest payload = null;
		MethodEnumeration method = null;
		try {
			payload = gson.fromJson(payloadStr, RPCRequest.class);
			log.trace("Received: "+ payload);

			method = MethodEnumeration.getMethodById(payload.getMethod());
			log.trace("Method invoking: "+method.name());

			method.invoke(payload,request,response);

		}catch (JsonSyntaxException e){
			log.error("Failed to parse payload request");
			log.debug("Error Received: "+payloadStr,e);
		}catch (NoSuchMethodException|InvocationTargetException|InstantiationException|IllegalAccessException e){
			log.error("Failed to invoke the requested payload method: \n\t"+ payload +"\n\t"+method.name(),e);
		}
	}
}
