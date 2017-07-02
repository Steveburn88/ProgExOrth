package de.schneefisch.fruas.database;

import com.mysql.jdbc.Statement;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.Salutation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

	private DBConnector dbc;
	
	public CustomerDAO() {
		try {
			this.dbc = new DBConnector();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int updateCustomer(Customer customer) throws SQLException {
		String query = "update personenkunde set vornamePersonenkunde = ?, "
				+ "nachnamePersonenkunde = ?, "
				+ "telefonPersonenkunde = ?, "
				+ "positionPersonenkunde = ?, "
				+ "abteilungPersonenkunde = ?, "
				+ "anredePersonenkunde = ?, "
				+ "emailPersonenkunde = ?, "
				+ "gebäudenummerPersonenkunde = ?, "
				+ "zimmernummerPersonenkunde = ?, "
				+ "faxPersonenkunde = ? "
				+ "where idPersonenkunde = ?;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
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
		statement.setInt(11, customer.getId());
		int updated = statement.executeUpdate();	
		return updated;
				
	}
	
	public int deleteCustomer(int customerId) throws SQLException {
		String query = "delete from personenkunde where idPersonenkunde = ?;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		statement.setInt(1, customerId);
		int removed = statement.executeUpdate();
		return removed;
	}
	
	public Customer searchCustomerById(int customerId) throws SQLException {
		String query = "select * from personenkunde where idPersonenkunde = ?;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
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
					 rs.getString("gebäudenummerPersonenkunde"), rs.getString("zimmernummerPersonenkunde"), rs.getString("faxPersonenkunde"));
		}
		return customer;
	
	}
	
	
	public List<Customer> searchCustomersByName(String name) throws SQLException {
		List<Customer> customerList = new ArrayList<Customer>();
		String query = "select * from personenkunde where vornamePersonenkunde like ? or nachnamePersonenkunde like ?;";
		
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
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
					 rs.getString("gebäudenummerPersonenkunde"), rs.getString("zimmernummerPersonenkunde"), rs.getString("faxPersonenkunde"));
			customerList.add(customer);
		}
		return customerList;
	}
	
	public List<Customer> selectAllCustomers() throws SQLException {
		List<Customer> customerList = new ArrayList<Customer>();
		String query = "select * from personenkunde;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
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
					 rs.getString("gebäudenummerPersonenkunde"), rs.getString("zimmernummerPersonenkunde"), rs.getString("faxPersonenkunde"));
			customerList.add(customer);
		}
		return customerList;
	}
	
	public Customer insertCustomer(Customer customer) throws SQLException {

		String query = "insert into personenkunde (vornamePersonenkunde, nachnamePersonenkunde, telefonPersonenkunde, "
				+ "positionPersonenkunde, abteilungPersonenkunde, anredePersonenkunde, "
				+ "emailPersonenkunde, gebäudenummerPersonenkunde, zimmernummerPersonenkunde, faxPersonenkunde,"
				+ "idFirmenkunde, idStandort)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

		PreparedStatement statement = dbc.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

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
		System.out.println("inserted " + results +" customer:" + customer);
		return customer;
	}

	
	
}
