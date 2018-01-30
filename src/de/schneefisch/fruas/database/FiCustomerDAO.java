package de.schneefisch.fruas.database;

import com.mysql.jdbc.Statement;

import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.FiCustomer;
import de.schneefisch.fruas.model.Salutation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FiCustomerDAO {

	private DBConnector dbc;

	public FiCustomerDAO() {
		try {
			this.dbc = new DBConnector();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FiCustomer selectFiCustomer(int fiCustomerId) throws SQLException {
		String query = "select * from firmenkunde where idFirmenkunde = ?;";

		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		statement.setInt(1, fiCustomerId);
		ResultSet rs = statement.executeQuery();
		FiCustomer fiCustomer = null;
		if (rs.next()) {
			fiCustomer = new FiCustomer(rs.getInt("idFirmenkunde"), rs.getString("nameFirmenkunde"));
		}

		return fiCustomer;
	}

	public FiCustomer insertFiCustomer(FiCustomer fiCustomer) throws SQLException {
		String query = "insert into firmenkunde (nameFirmenkunde)" + " values (?);";

		PreparedStatement statement = dbc.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, fiCustomer.getName());
		int results = statement.executeUpdate();
		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				fiCustomer.setId(generatedKeys.getInt(1));
			} else {
				throw new SQLException("Creating user failed, no ID obtained.");
			}
		}
		System.out.println("inserted " + results + " fiCustomer:" + fiCustomer);
		return fiCustomer;
	}

	public List<FiCustomer> selectAllFiCustomers() throws SQLException {
		List<FiCustomer> fiCustomerList = new ArrayList<FiCustomer>();
		String query = "select * from firmenkunde;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			FiCustomer fiCustomer = new FiCustomer(rs.getInt("idFirmenkunde"), rs.getString("nameFirmenkunde"));
			fiCustomerList.add(fiCustomer);
		}
		return fiCustomerList;
	}

	public List<FiCustomer> searchFiCustomersByName(String text) throws SQLException {
		List<FiCustomer> customerList = new ArrayList<FiCustomer>();
		String query = "select * from firmenkunde where nameFirmenkunde like ?;";

		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		statement.setString(1, "%" + text + "%");

		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			FiCustomer customer = new FiCustomer(rs.getInt("idFirmenkunde"), rs.getString("nameFirmenkunde"));
			customerList.add(customer);
		}
		return customerList;
	}

	public int deleteFiCustomer(int custId) throws SQLException {
		String query1 = "delete from standort where idFirmenkunde = ?";
		PreparedStatement statement1 = dbc.getConnection().prepareStatement(query1);
		statement1.setInt(1, custId);
		int removed1 = statement1.executeUpdate();

		String query2 = "delete from firmenkunde where idFirmenkunde = ?;";
		PreparedStatement statement2 = dbc.getConnection().prepareStatement(query2);
		statement2.setInt(1, custId);
		int removed2 = statement2.executeUpdate();
		return removed1 + removed2;
	}

	public int updateFiCustomer(FiCustomer edited) throws SQLException {
		String query = "update firmenkunde set nameFirmenkunde = ? "

				+ "where idFirmenkunde = ?;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		statement.setString(1, edited.getName());
		statement.setInt(2, edited.getId());

		int updated = statement.executeUpdate();
		return updated;

	}

}
