package de.schneefisch.fruas.database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mysql.jdbc.Statement;

import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.FiCustomer;
import de.schneefisch.fruas.model.Location;
import de.schneefisch.fruas.model.Salutation;

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
	
	public int updateCustomer(Customer customer) throws SQLException {
		String query = "update personenkunde set vornamePersonenkunde = ?, "
				+ "nachnamePersonenkunde = ?, "
				+ "telefonPersonenkunde = ?, "
				+ "positionPersonenkunde = ?, "
				+ "abteilungPersonenkunde = ?, "
				+ "anredePersonenkunde = ?, "
				+ "emailPersonenkunde = ?, "
				+ "geb‰udenummerPersonenkunde = ?, "
				+ "zimmernummerPersonenkunde = ?, "
				+ "faxPersonenkunde = ? "
				+ "where idPersonenkunde = ?;";
		PreparedStatement statement = connection.prepareStatement(query);
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
		System.out.println(customer.getFaxNumber());
		statement.setInt(11, customer.getId());
		System.out.println(customer.getId());
		int updated = statement.executeUpdate();	
		return updated;
				
	}
	
	public int deleteCustomer(int customerId) throws SQLException {
		String query = "delete from personenkunde where idPersonenkunde = ?;";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, customerId);
		int removed = statement.executeUpdate();
		return removed;
	}
	
	public Customer searchCustomerById(int customerId) throws SQLException {
		String query = "select * from personenkunde where idPersonenkunde = ?;";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, customerId);
		ResultSet rs = statement.executeQuery();
		Customer customer = new Customer();
		while(rs.next()) {
			Salutation salutation = null;
			if(rs.getString("anredePersonenkunde").equals("Herr")) {
				salutation = Salutation.Herr;
			}
			else salutation = Salutation.Frau;
			
			customer =  new Customer(rs.getInt("idPersonenkunde"), rs.getInt("idFirmenkunde"), rs.getInt("idStandort"), salutation, rs.getString("vornamePersonenkunde"),
					rs.getString("nachnamePersonenkunde"),	rs.getString("telefonPersonenkunde"),	rs.getString("emailPersonenkunde"),
					 rs.getString("positionPersonenkunde"), rs.getString("abteilungPersonenkunde"),
					 rs.getString("geb‰udenummerPersonenkunde"), rs.getString("zimmernummerPersonenkunde"), rs.getString("faxPersonenkunde"));
		}
		return customer;
	
	}
	
	
	public List<Customer> searchCustomersByName(String name) throws SQLException {
		List<Customer> customerList = new ArrayList<Customer>();
		String query = "select * from personenkunde where vornamePersonenkunde like ? or nachnamePersonenkunde like ?;";
		
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setString(1, "%" + name + "%");
		statement.setString(2, "%" + name + "%");
		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			Salutation salutation = null;
			if(rs.getString("anredePersonenkunde").equals("Herr")) {
				salutation = Salutation.Herr;
			}
			else salutation = Salutation.Frau;
			
			Customer customer = new Customer(rs.getInt("idPersonenkunde"), rs.getInt("idFirmenkunde"), rs.getInt("idStandort"), salutation, rs.getString("vornamePersonenkunde"),
					rs.getString("nachnamePersonenkunde"),	rs.getString("telefonPersonenkunde"),	rs.getString("emailPersonenkunde"),
					 rs.getString("positionPersonenkunde"), rs.getString("abteilungPersonenkunde"),
					 rs.getString("geb‰udenummerPersonenkunde"), rs.getString("zimmernummerPersonenkunde"), rs.getString("faxPersonenkunde"));
			customerList.add(customer);
		}
		return customerList;
	}
	
	public List<Customer> selectAllCustomers() throws SQLException {
		List<Customer> customerList = new ArrayList<Customer>();
		String query = "select * from personenkunde;";
		PreparedStatement statement = connection.prepareStatement(query);
		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			Salutation salutation = null;
			if(rs.getString("anredePersonenkunde").equals("Herr")) {
				salutation = Salutation.Herr;
			}
			else salutation = Salutation.Frau;
			
			Customer customer = new Customer(rs.getInt("idPersonenkunde"), rs.getInt("idFirmenkunde"), rs.getInt("idStandort"), salutation, rs.getString("vornamePersonenkunde"),
					rs.getString("nachnamePersonenkunde"),	rs.getString("telefonPersonenkunde"),	rs.getString("emailPersonenkunde"),
					 rs.getString("positionPersonenkunde"), rs.getString("abteilungPersonenkunde"),
					 rs.getString("geb‰udenummerPersonenkunde"), rs.getString("zimmernummerPersonenkunde"), rs.getString("faxPersonenkunde"));
			customerList.add(customer);
		}
		return customerList;
	}
	
	public Location selectFiCustomerLocation(int fiCustomerId) throws SQLException {
		String query = "select * from standort where idFirmenkunde = ?;";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, fiCustomerId);
		ResultSet rs = statement.executeQuery();
		Location location = null;
		while(rs.next()) {
			location = new Location(rs.getInt("idStandort"), rs.getInt("idFirmenkunde"), rs.getString("plzStandort"), rs.getString("stadtStandort"), rs.getString("postfachStandort"),
					rs.getString("straﬂeStandort"), rs.getString("hausnummerStandort"));
		}	
		return location;
	}
	
	public FiCustomer selectFiCustomer(int fiCustomerId) throws SQLException {
		String query = "select * from firmenkunde where idFirmenkunde = ?;";
		
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, fiCustomerId);
		ResultSet rs = statement.executeQuery();
		FiCustomer fiCustomer = new FiCustomer(rs.getInt("idFirmenkunde"), rs.getString("nameFirmenkunde"));
		return fiCustomer;
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
