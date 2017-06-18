package de.schneefisch.fruas.database;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Statement;

import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.FiCustomer;
import de.schneefisch.fruas.model.Location;

public class DBConnector {

	private Connection connection;

	public DBConnector() throws Exception {
		System.out.println(new File(".").getAbsolutePath());
		Properties properties = new Properties();
		properties.load(new FileInputStream("connector.properties"));

		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		String url = properties.getProperty("url");

		setConnection(DriverManager.getConnection(url, username, password));

		System.out.println("Connected to " + url + " as User " + username);

	}

	public FiCustomer insertFiCustomer(FiCustomer fiCustomer) throws SQLException {
		String query = "insert into firmenkunde (nameFirmenkunde)" + " values (?);";

		PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, fiCustomer.getName());
		int results = statement.executeUpdate();
		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				fiCustomer.setId(generatedKeys.getInt(1));
			} else {
				throw new SQLException("Creating user failed, no ID obtained.");
			}
		}
		System.out.println("inserted:" + fiCustomer);
		return fiCustomer;
	}

	public Location insertLocation(Location location) throws SQLException {

		String query = "insert into standort (idFirmenkunde, plzStandort, stadtStandort, postFachStandort, straﬂeStandort, hausnummerStandort)"
				+ " values (?, ?, ?, ?, ?, ?);";

		PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, location.getFiKuId());
		statement.setString(2, location.getPostalCode());
		statement.setString(3, location.getCity());
		statement.setString(4, location.getPostBox());
		statement.setString(5, location.getStreet());
		statement.setString(6, location.getHouseNumber());
		int results = statement.executeUpdate();

		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				location.setId(generatedKeys.getInt(1));
			} else {
				throw new SQLException("Creating user failed, no ID obtained.");
			}
		}
		System.out.println("inserted:" + location);
		return location;
	}

	public Customer insertCustomer(Customer customer) throws SQLException {

		String query = "insert into personenkunde (vornamePersonenkunde, nachnamePersonenkunde, telefonPersonenkunde, "
				+ "positionPersonenkunde, abteilungPersonenkunde, anredePersonenkunde, "
				+ "emailPersonenkunde, geb‰udenummerPersonenkunde, zimmernummerPersonenkunde, faxPersonenkunde,"
				+ "idFirmenkunde, idStandort)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

		statement.setString(1, customer.getFirstName());
		statement.setString(2, customer.getLastName());
		statement.setString(3, customer.getPhoneNumber());
		statement.setString(4, customer.getPosition());
		statement.setString(5, customer.getDepartment());
		statement.setString(6, customer.getSalutation().toString());
		statement.setString(7, customer.getEmail());
		statement.setString(8, customer.getBuildingNumber());
		statement.setString(9, customer.getRoomNumber());
		statement.setString(10, customer.getFaxNumber());
		statement.setInt(11, customer.getFiKuId());
		statement.setInt(12, customer.getLocationId());

		int results = statement.executeUpdate();
		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				customer.setId(generatedKeys.getInt(1));
			} else {
				throw new SQLException("Creating user failed, no ID obtained.");
			}
		}
		System.out.println("inserted:" + customer);
		return customer;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
