package com.gotrecha.dbms.connection;

import com.gotrecha.util.properties.PropertiesManager;
import com.gotrecha.util.traits.UtilTrait;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Created by dustin on 4/26/14.
 */
public class ConnectionManager implements UtilTrait {
	private static ConnectionManager instance;

	private final Logger log = log();
	private final DataSource dataSource;


	protected ConnectionManager() {
		dataSource = getDataSource();
	}

	private DataSource getDataSource() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/EaterDB");
			return ds;
		}catch (Exception ex){
			log.error("Failed to get data source: ",ex);
		}
		return null;
	}

	public static ConnectionManager getInstance() {
		return instance == null ? (instance = new ConnectionManager()) : instance;
	}

	public synchronized static Connection getConnection() {
		return getInstance().getConnectionFromPool();
	}

	protected Connection getConnectionFromPool() {
		try {
			if (dataSource != null) {
				return dataSource.getConnection();
			}
		}catch (Exception e){
			log.error("Major error; failed to obtain connection",e);
			invalidate();
		}
		return null;
	}

	public static void invalidate(){
		instance = null;
	}
	private void closeConnection(Connection connection){
		try{
		    connection.close();
		}catch(Exception ex){
		    log.error("Failed to close connection"+ex);
		}
	}

	public synchronized static void releaseConnection(Connection connection) {
		getInstance().closeConnection(connection);
	}

}
