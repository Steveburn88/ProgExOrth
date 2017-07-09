package de.schneefisch.fruas.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.schneefisch.fruas.model.DeliveryNote;
import de.schneefisch.fruas.model.License;
import de.schneefisch.fruas.model.Maintenance;

public class DeliveryNoteDAO {
	private DBConnector dbc;

	public DeliveryNoteDAO() {
		try {
			this.dbc = new DBConnector();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DeliveryNote insertDeliveryNote(DeliveryNote deliveryNote) throws SQLException {

		String query = "insert into lieferschein (datumLieferschein)" + " values(?);";

		PreparedStatement statement = dbc.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

		statement.setDate(1, deliveryNote.getDate());

		int results = statement.executeUpdate();
		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				deliveryNote.setId(generatedKeys.getInt(1));
			} else {
				throw new SQLException("Creating license failed, no ID obtained.");
			}
		}
		System.out.println("inserted " + results + " deliveryNote:" + deliveryNote);
		return deliveryNote;

	}

	public List<DeliveryNote> selectAllDeliveryNotes() throws SQLException {

		List<DeliveryNote> dnList = new ArrayList<DeliveryNote>();
		String query = "select * from lieferschein;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			DeliveryNote dn = new DeliveryNote(rs.getInt("idLieferschein"), rs.getDate("datumLieferschein"));
			dnList.add(dn);
		}
		return dnList;
	}

	public DeliveryNote selectDeliveryNoteById(int dnId) throws SQLException {
		 String query = "select * from lieferschein where idLieferschein = ?;";
	        PreparedStatement statement = dbc.getConnection().prepareStatement(query);
	        statement.setInt(1, dnId);
	        ResultSet rs = statement.executeQuery();
	        DeliveryNote deliveryNote = null;
	        while(rs.next()) {
	        	deliveryNote =  new DeliveryNote(rs.getInt("idLieferschein"),
	                    rs.getDate("datumLieferschein"));
	        }
	        return deliveryNote;
	}

	public int deleteDeliveryNote(int id) throws SQLException {
		String query = "delete from lieferschein where idLieferschein = ?;";
        PreparedStatement statement = dbc.getConnection().prepareStatement(query);
        statement.setInt(1, id);
        int removed = statement.executeUpdate();
        return removed;
	}

}
