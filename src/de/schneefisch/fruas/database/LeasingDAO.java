package de.schneefisch.fruas.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.Leasing;

public class LeasingDAO {
	private DBConnector dbc;

	public LeasingDAO() {
		try {
			this.dbc = new DBConnector();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Leasing insertLeasing(Leasing leasing) throws SQLException {
		String query = "insert into leasing (idPersonenkunde, idProdukt, startDatumLeasing, "
				+ "ersteRechnungDatumLeasing, rechnungsBetragLeasing, faelligRechnungLeasing, "
				+ "anzahlRechnungenLeasing, letzteRechnungDatum)" + " values (?, ?, ?, ?, ?, ?, ?, ?);";		
		PreparedStatement statement = dbc.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);		
		statement.setInt(1, leasing.getCustomerId());
		statement.setInt(2, leasing.getProductId());
		statement.setDate(3, leasing.getStartDate());
		statement.setDate(4, leasing.getFirstBillDate());
		statement.setFloat(5, leasing.getBillPayment());
		statement.setDate(6, leasing.getDueBillDate());
		statement.setInt(7, leasing.getNumberOfBills());
		statement.setDate(8, leasing.getRecentBillDate());
		int results = statement.executeUpdate();
		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				leasing.setId(generatedKeys.getInt(1));
			} else {
				throw new SQLException("Creating user failed, no ID obtained.");
			}
		}
		System.out.println("inserted " + results +" leasing:" + leasing);
		return leasing;
	}
	
	public List<Leasing> selectAllLeasings() throws SQLException{
		List<Leasing> leasingList = new ArrayList<Leasing>();
		String query = "select * from leasing;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			Leasing leasing = new Leasing(rs.getInt("idLeasing"), rs.getInt("idPersonenkunde"), rs.getInt("idProdukt"), rs.getDate("startDatumLeasing"), 
					rs.getDate("ersteRechnungDatumLeasing"), rs.getDate("letzteRechnungDatum"), rs.getDate("faelligRechnungLeasing"), rs.getInt("anzahlRechnungenLeasing"),
					rs.getFloat("rechnungsBetragLeasing"));
			leasingList.add(leasing);
		}
		return leasingList;
	}
	
	public Leasing selectLeasingById(int id) throws SQLException{
		List<Leasing> leasingList = new ArrayList<Leasing>();
		String query = "select * from leasing where idLeasing = ?;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		statement.setInt(1, id);
		ResultSet rs = statement.executeQuery();
		Leasing leasing = null;
		while(rs.next()) {
			leasing = new Leasing(rs.getInt("idLeasing"), rs.getInt("idPersonenkunde"), rs.getInt("idProdukt"), rs.getDate("startDatumLeasing"), 
					rs.getDate("ersteRechnungDatumLeasing"), rs.getDate("letzteRechnungDatum"), rs.getDate("faelligRechnungLeasing"), rs.getInt("anzahlRechnungenLeasing"),
					rs.getFloat("rechnungsBetragLeasing"));
			leasingList.add(leasing);
		}
		return leasing;
	}
	
	public int updateLeasing(Leasing leasing) throws SQLException {
		String query = "update leasing set idPersonenkunde = ?, "
				+ "idProdukt = ?, "
				+ "startDatumLeasing = ?, "
				+ "ersteRechnungDatumLeasing = ?, "
				+ "rechnungsBetragLeasing = ?, "
				+ "faelligRechnungLeasing = ?, "
				+ "anzahlRechnungenLeasing = ?, "
				+ "letzteRechnungDatum = ? "
				+ "where idLeasing = ?;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		statement.setInt(1, leasing.getCustomerId());
		statement.setInt(2, leasing.getProductId());
		statement.setDate(3, leasing.getStartDate());
		statement.setDate(4, leasing.getFirstBillDate());
		statement.setFloat(5, leasing.getBillPayment());
		statement.setDate(6, leasing.getDueBillDate());
		statement.setInt(7, leasing.getNumberOfBills());
		statement.setDate(8, leasing.getRecentBillDate());
		statement.setInt(9, leasing.getId());
		int updated = statement.executeUpdate();	
		return updated;				
	}
	
	public int deleteLeasing(int id) throws SQLException {
		String query = "delete from leasing where idLeasing = ?;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		statement.setInt(1, id);
		int removed = statement.executeUpdate();
		return removed;
	}
	
}
