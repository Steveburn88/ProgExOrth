package de.schneefisch.fruas.database;

import com.mysql.jdbc.Statement;
import de.schneefisch.fruas.model.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationDAO {
	private DBConnector dbc;

	public LocationDAO() {
		try {
			this.dbc = new DBConnector();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Location> findLocationsByFiCustomerId(int fiCustomerId) throws SQLException {
		List<Location> locationList = new ArrayList<Location>();
		String query = "select * from standort where idFirmenkunde = ?;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		statement.setInt(1, fiCustomerId);
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()) {
			Location location  = new Location(rs.getInt("idStandort"), rs.getInt("idFirmenkunde"), rs.getString("plzStandort"), rs.getString("stadtStandort"), rs.getString("postfachStandort"),
					rs.getString("strasseStandort"), rs.getString("hausnummerStandort"));
			locationList.add(location);
		}	
		return locationList;
	}
	
	
	public Location findLocationByFiCustomerId(int fiCustomerId) throws SQLException {
		String query = "select * from standort where idFirmenkunde = ?;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		statement.setInt(1, fiCustomerId);
		ResultSet rs = statement.executeQuery();
		Location location = null;
		while(rs.next()) {
			location = new Location(rs.getInt("idStandort"), rs.getInt("idFirmenkunde"), rs.getString("plzStandort"), rs.getString("stadtStandort"), rs.getString("postfachStandort"),
					rs.getString("strasseStandort"), rs.getString("hausnummerStandort"));
		}	
		return location;
	}
	
	public Location insertLocation(Location location) throws SQLException {

		String query = "insert into standort (idFirmenkunde, plzStandort, stadtStandort, postFachStandort, strasseStandort, hausnummerStandort)"
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

	public int deleteLocation(int custId) throws SQLException{
		String query = "delete from location where idLocation = ?;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		statement.setInt(1, custId);
		int removed = statement.executeUpdate();
		return removed;
	}

	public int updateLocation(Location edited) throws SQLException{
		String query = "update standort set plzStandort = ?, "				
				+ "stadtStandort = ?, "
				+ "postfachStandort = ?, "
				+ "strasseStandort = ?, "
				+ "hausnummerStandort = ?"
				+ "where idStandort = ?;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		statement.setString(1, edited.getPostalCode());
		statement.setString(2, edited.getCity());
		statement.setString(3, edited.getPostBox());
		statement.setString(4, edited.getStreet());
		statement.setString(5, edited.getHouseNumber());
		
		statement.setInt(6, edited.getId());
		int updated = statement.executeUpdate();	
		return updated;	
	}
}
