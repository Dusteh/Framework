package com.gotrecha.eater;

import com.gotrecha.dbms.connection.ConnectionManager;
import com.gotrecha.util.properties.PropertiesManager;
import com.gotrecha.util.traits.UtilTrait;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Date;

/**
 * Created by dustin on 4/29/14.
 */
public class ApplicationContext implements ServletContextListener, UtilTrait {
	private final Logger log = log();

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		log.info("Starting system initialization: " + (new Date()));
		try {

			PropertiesManager.getInstance();//Initialize the properties
			ConnectionManager.getInstance();//Initialize the DBMS system
		} catch (Throwable ex) {
			log.error("Exception in initialization", ex);
		}

		log.info("Finished system initialization: " + (new Date()));
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		log.info("Starting system shutdown: " + (new Date()));


		log.info("Finished system shutdown: " + (new Date()));
	}
}
