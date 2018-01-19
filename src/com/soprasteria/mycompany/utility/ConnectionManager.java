package com.soprasteria.mycompany.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class ConnectionManager {

	/**
	 * 
	 * @return
	 */
	public static Connection getConnection() { // create connection with DB
		Connection conn = null;
		try {
			String url = "jdbc:oracle:thin:@localhost:1521:xe"; // select DB
			String id = "hr";
			String psw = "hr";
			conn = DriverManager.getConnection(url, id, psw); // opening the connection with DB
		} catch (Exception e) {
			e.printStackTrace();
			conn = null;
		}
		return conn;

	}

	/**
	 * 
	 * @param connection
	 */
	public static void closeConnection(Connection connection) { // close connection from DB
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * getDataSource --> set url, user, password and driver
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static DataSource getDataSource() throws SQLException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		dataSource.setUsername("hr");
		dataSource.setPassword("hr");
		return dataSource;
	}
}
