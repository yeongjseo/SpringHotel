package com.springhotel.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/*
import org.apache.tomcat.dbcp.dbcp2.ConnectionFactory;
import org.apache.tomcat.dbcp.dbcp2.DriverManagerConnectionFactory;
import org.apache.tomcat.dbcp.dbcp2.PoolableConnection;
import org.apache.tomcat.dbcp.dbcp2.PoolableConnectionFactory;
import org.apache.tomcat.dbcp.dbcp2.PoolingDriver;
import org.apache.tomcat.dbcp.pool2.impl.GenericObjectPool;
import org.apache.tomcat.dbcp.pool2.impl.GenericObjectPoolConfig;
*/


public class EnvListener implements ServletContextListener {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		ServletContext context = sce.getServletContext();
		String envLoc = context.getInitParameter("envConfigLocation");
		Properties prop = new Properties();
		ClassPathResource resource = new ClassPathResource(envLoc);
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
			prop.load(reader);
		} catch (IOException e) {
			throw new RuntimeException("config load fail", e);
		}
		
		Iterator<Object> keyIter = prop.keySet().iterator();
		while (keyIter.hasNext()) {
			String key = (String) keyIter.next();
			String val = prop.getProperty(key);
			logger.debug(String.format("ENV: %s=%s", key, val));
			if (key.substring(0, 1).equals("#"))
				continue;
			
			try {
				Env.put(key, val);
			} catch (Exception e) {}
		}
		
		String uploadPathAbsolute = (String) Env.get("uploadPathAbsolute");
		String uploadPath = (String) Env.get("uploadPath");
		if (! uploadPathAbsolute.equals("true")) {
			Env.put("uploadPath", context.getRealPath(File.separator + uploadPath));
			logger.debug(String.format("ENV: %s=%s\n", "uploadPath", (String)Env.get("uploadPath")));
		}
		
		
	}

	

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	
	
	}

}
