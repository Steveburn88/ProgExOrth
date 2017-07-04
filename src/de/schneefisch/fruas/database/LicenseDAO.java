package de.schneefisch.fruas.database;

import de.schneefisch.fruas.model.License;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LicenseDAO {
	private DBConnector dbc;
	
	public LicenseDAO() {
		try {
			this.dbc = new DBConnector();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<License> selectAllLicenses() throws SQLException {
		
		List<License> licenseList = new ArrayList<License>();
		String query = "select * from lizenz;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			License license = new License(rs.getInt("idLizenz"), rs.getInt("idPersonenkunde"), rs.getInt("idProdukt"), 
					rs.getString("installationsschluesselLizenz"), rs.getBoolean("verkauftStatusLizenz"), rs.getFloat("rabattLizenz"),
					rs.getDate("verkaufsdatumLizenz"), rs.getDate("ablaufdatumLizenz"), rs.getInt("idMaintenance"));
			licenseList.add(license);			
		}
		return licenseList;			
	}

	public License insertLicense(License license) throws SQLException {

        String query = "insert into lizenz (idProdukt, installationsschluesselLizenz)" + " values(?, ?);";

        PreparedStatement statement = dbc.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        statement.setInt(1, license.getProductId());
        statement.setString(2, license.getKey());
      
        int results = statement.executeUpdate();
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                license.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating license failed, no ID obtained.");
            }
        }
        System.out.println("inserted " + results +" license:" + license);
        return license;
		
	}
	
	
}