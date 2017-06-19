package de.schneefisch.fruas.database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;



public class DBConnector {

	private Connection connection;

	public DBConnector() throws Exception {
		Properties properties = new Properties();
		properties.load(new FileInputStream("connector.properties"));
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		String url = properties.getProperty("url");
		setConnection(DriverManager.getConnection(url, username, password));
		System.out.println("Connected to " + url + " as User " + username);
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
