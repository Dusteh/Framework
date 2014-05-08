package com.gotrecha.eater.api.methods.security;

import com.google.gson.JsonObject;
import com.gotrecha.api.API;
import com.gotrecha.api.MessageCodeEnumeration;
import com.gotrecha.api.RPCRequest;
import com.gotrecha.eater.bean.user.User;
import com.gotrecha.eater.sql.GetUserByLogin;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by dustin on 4/26/14.
 */
public class Login extends API {
	private final Logger log = log();
	private final String username;
	private final String password;

	public Login(HttpServletRequest request, HttpServletResponse response, RPCRequest payload) {
		super(request, response, payload);
		JsonObject data = payload.getParams();
		username = data.has("username") ? data.get("username").getAsString() : null;
		password = data.has("password") ? data.get("password").getAsString() : null;
	}

	@Override
	public void process() {
		try{
			if(username == null || password == null){
				log.info("Error with user, user name or password was null");
				writeError("Error with user, user name or password was null",MessageCodeEnumeration.FAILED_VALIDATION);
			}

			User user = (new GetUserByLogin(username)).execute();
			if(user == null){
				log.info("Error with user, result was null");
				writeError("Error with user, result was null", MessageCodeEnumeration.FAILED_VALIDATION);
			} else {
				boolean valid = validateCredentials(user);
				if(valid){
					//TODO - Stat recording
					writeResult(user);
				}else{
					writeError("User was unable to be validated",MessageCodeEnumeration.FAILED_VALIDATION);
				}
			}
		}catch(Exception ex){
			log.error("Failed to get user for login: "+username,ex);
		}
	}

	private boolean validateCredentials(User user) throws IOException{
		if(password != null && user != null && user.getPassword() != null && user.getPassword().length()>0) {
			byte[] attempt = getHashForString(password);
			byte[] password = decode(user.getPassword());
			return Arrays.equals(attempt, password);
		}
		return false;
	}
}
