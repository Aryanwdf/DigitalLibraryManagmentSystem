package com.dls.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class read application.properties file which is a config file having all
 * the database connection related properties. Connection MySql Database.
 * 
 * @author Aryan Arora
 *
 */
public class DatabaseConfig {

	static Properties prop = new Properties();
	static {
		try {
			FileInputStream input = new FileInputStream("application.properties");
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final static String DRIVER_NAME = prop.getProperty("db.driver");
	public final static String DB_HOST = prop.getProperty("db.host");
	public final static String DB_PORT = prop.getProperty("db.port");
	public final static String DB_NAME = prop.getProperty("db.name");
	public final static String DB_USER_NAME = prop.getProperty("db.username");
	public final static String DB_PASSWORD = prop.getProperty("db.password");
	public final static String SSL_MODE = prop.getProperty("db.sslmode");
	public final static String CONNECTION_STRING = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + SSL_MODE;

}