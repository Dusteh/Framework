package com.gotrecha.api;

/**
 * Created by dustin on 4/26/14.
 */
public interface APIExtension {


//	public static final String ERROR_MESSAGE_JSON = "{\"messageType\":,\"errorCode\":%1$d,\"errorMessage\":\"%2$s\"}";
//	public static final String RESPONSE_MESSAGE_JSON = "{\"messageType\":,\"responseId\":%1$d,\"responseMessage\":\"%2$s\",\"response\":%3$s}";

	default RPCResult prepareMessage(long responseId,Object data){
		RPCResultFactory factory = RPCResultFactory.getFactory();
		return factory.createResult(responseId,data);
	}

	default RPCResult prepareErrorMessage(long responseId,String message, MessageCodeEnumeration messageCode){
		RPCResultFactory factory = RPCResultFactory.getFactory();
		RPCError error = new RPCError(messageCode.getValue(),message,null);
		return factory.createError(responseId,error);
	}

}
