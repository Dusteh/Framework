package com.gotrecha.api;

import com.gotrecha.eater.api.methods.security.Login;
import com.gotrecha.eater.api.methods.util.GetAPIMethods;
import com.gotrecha.eater.api.methods.util.GetVersion;

import javax.servlet.http.*;
import java.lang.reflect.*;

/**
 * Created by dustin on 4/26/14.
 */
public enum MethodEnumeration {
	GET_VERSION(1,GetVersion.class),
	GET_API_METHODS(2,GetAPIMethods.class),
	LOGIN(3, Login.class);


	private final long methodUUID;
	private final Class apiCall;

	MethodEnumeration(long methodUUID,Class apiCall) {
		this.methodUUID = methodUUID;
		this.apiCall = apiCall;
	}

	public long getMethodUUID() {
		return methodUUID;
	}

	public static MethodEnumeration getMethodById(long id){
		for (MethodEnumeration methodEnumeration : MethodEnumeration.values()) {
			if(methodEnumeration.getMethodUUID() == id){
				return methodEnumeration;
			}
		}
		return null;
	}

	public void invoke(RPCRequest payload, HttpServletRequest request, HttpServletResponse response) throws NoSuchMethodException,IllegalAccessException,InvocationTargetException,InstantiationException{
		API api = (API)apiCall.getConstructor(HttpServletRequest.class, HttpServletResponse.class,RPCRequest.class).newInstance(request,response, payload);
		api.process();
	}
}
