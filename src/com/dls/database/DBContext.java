package com.dls.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.dls.exception.CustomException;

/**
 * This class create connection object using the connection properties from
 * DatabaseConfig file.
 * 
 * @author Aryan Arora
 *
 */
public class DBContext {
	private static Connection connection;

	public static void setConnection(Connection connection) {
		DBContext.connection = connection;
	}

	public static Connection getConnection() throws CustomException {
		try {

			Class.forName(DatabaseConfig.DRIVER_NAME);

			connection = DriverManager.getConnection(DatabaseConfig.CONNECTION_STRING, DatabaseConfig.DB_USER_NAME,
					DatabaseConfig.DB_PASSWORD);
		} catch (SQLException | ClassNotFoundException e) {

			throw new CustomException("Database connection failed : " + e.getMessage());

		}

		return connection;
	}
}
