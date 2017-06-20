package de.schneefisch.fruas.database;

import com.mysql.jdbc.Statement;
import de.schneefisch.fruas.model.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationDAO {
	private DBConnector dbc;

	public LocationDAO() {
		try {
			this.dbc = new DBConnector();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public Location findLocationByFiCustomerId(int fiCustomerId) throws SQLException {
		String query = "select * from standort where idFirmenkunde = ?;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		statement.setInt(1, fiCustomerId);
		ResultSet rs = statement.executeQuery();
		Location location = null;
		while(rs.next()) {
			location = new Location(rs.getInt("idStandort"), rs.getInt("idFirmenkunde"), rs.getString("plzStandort"), rs.getString("stadtStandort"), rs.getString("postfachStandort"),
					rs.getString("straßeStandort"), rs.getString("hausnummerStandort"));
		}	
		return location;
	}
	
	public Location insertLocation(Location location) throws SQLException {

		String query = "insert into standort (idFirmenkunde, plzStandort, stadtStandort, postFachStandort, straßeStandort, hausnummerStandort)"
				+ " values (?, ?, ?, ?, ?, ?);";

		PreparedStatement statement = dbc.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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
		System.out.println("inserted " + results +" location:" + location);
		return location;
	}
}
