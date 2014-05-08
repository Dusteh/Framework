package com.gotrecha.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.gotrecha.util.traits.UtilTrait;
import jdk.nashorn.internal.parser.JSONParser;

/**
 * Created by dustin on 5/3/14.
 */
public class RPCResultFactory implements UtilTrait{

	private static final RPCResultFactory instance = new RPCResultFactory();

	private RPCResultFactory(){}

	public static RPCResultFactory getFactory(){
		return instance;
	}

	public RPCResult createError(long id, RPCError error){
		return new RPCErrorResult(id,error);
	}

	public RPCResult createResult(long id, Object result){
		return new RPCSuccessResult(id,result);
	}

	private class RPCErrorResult extends RPCResult{
		private final RPCError error;

		private RPCErrorResult(long id, RPCError error) {
			super(id);
			this.error = error;
		}

		public RPCError getErrorObj() {
			return error;
		}

		@Override
		public String toString() {
			return "RPCErrorResult{" +
					"error=" + error +
					"} " + super.toString();
		}
	}

	private class RPCSuccessResult extends RPCResult{
		private final Object result;

		private RPCSuccessResult(long id, Object result) {
			super(id);
			this.result = result;
		}


		@Override
		public String toString() {
			return "RPCSuccessResult{" +
					"result=" + result +
					"} " + super.toString();
		}
	}
}
