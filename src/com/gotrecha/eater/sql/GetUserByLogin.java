package com.gotrecha.eater.sql;

import com.gotrecha.dbms.SQL;
import com.gotrecha.eater.bean.user.User;
import com.gotrecha.util.traits.SimpleCacheTrait;
import com.gotrecha.util.traits.UtilTrait;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dustin on 4/30/14.
 */
public class GetUserByLogin extends SQL implements UtilTrait {
	private final Logger log = log();

	public GetUserByLogin(Object... parameters) {
		super(parameters);
	}

	@Override
	protected String sqlStatement() {
		return "select user_id,user_name,password,name_1,name_2,name_3 from user where user_name = ?";
	}

	@Override
	protected int getType() {
		return QUERY;
	}

	@Override
	protected Class getReturnType() {
		return User.class;
	}

	@Override
	protected <T> T processResult(List obj) {
		if(obj != null && obj.size() == 1){
			log.trace("Received object: "+obj.get(0));
			return (T)obj.get(0);
		}else if(obj != null && obj.size() > 1){
			log.info("Received more than one user login from:\n"+ sqlStatement() +"\nReturning first\n"+ Arrays.toString(parameters));
			return (T)obj.get(0);
		}
		return null;
	}

}
