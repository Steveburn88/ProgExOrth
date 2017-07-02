package de.schneefisch.fruas.database;

import com.mysql.jdbc.Statement;
import de.schneefisch.fruas.model.FiCustomer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		if(rs.next()) {
			fiCustomer  = new FiCustomer(rs.getInt("idFirmenkunde"), rs.getString("nameFirmenkunde"));
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
		System.out.println("inserted " + results + " fiCustomer:" +fiCustomer);
		return fiCustomer;
	}
}
