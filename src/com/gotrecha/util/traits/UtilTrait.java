package com.gotrecha.util.traits;


import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;


/**
 * Created by dustin on 3/9/14.
 */
public interface UtilTrait {
	public GsonBuilder builder = new GsonBuilder();
	public Random random = new Random(System.currentTimeMillis());
	/**
	 * Default method for getting a logger typed to the invoking class
	 * @return a Log4j initialized logger
	 */
	default public Logger log() {
		return LogManager.getLogger(this.getClass());
	}

	default public GsonBuilder getGsonBuilder(){
		return builder;
	}

	default public Gson getGson(){
		return builder.create();
	}

	default public JsonElement getJsonElement(String str){
		JsonParser parser = new JsonParser();
		return parser.parse(str);
	}

	default public Integer safeIntParse(String value,int defaultValue){
		try{
			return Integer.parseInt(value);
		}catch(Exception ex){
		    log().trace("Failed to parse, returning default value of: "+defaultValue);
		}
		return defaultValue;
	}

	default public long getRandomLong(){
		return random.nextLong();
	}

	default public byte[] getHashForString(String value){
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			return digest.digest(value.getBytes());
		}catch(NoSuchAlgorithmException e){
			log().error("Failed to instantiate a known algorithm?",e);
		}
		return new byte[0];
	}

	default String encode(byte[] src){
		return Base64.getEncoder().encodeToString(src);
	}

	default byte[] decode(String src){
		return Base64.getDecoder().decode(src);
	}
}
