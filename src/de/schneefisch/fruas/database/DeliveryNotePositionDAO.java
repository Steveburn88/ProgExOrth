package de.schneefisch.fruas.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.schneefisch.fruas.model.DeliveryNote;
import de.schneefisch.fruas.model.DeliveryNotePosition;

public class DeliveryNotePositionDAO {
	private DBConnector dbc;

	public DeliveryNotePositionDAO() {
		try {
			this.dbc = new DBConnector();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public DeliveryNotePosition insertDeliveryNotePosition(DeliveryNotePosition deliveryNotePosition) throws SQLException {

		String query = "insert into lieferschein_pos (idLieferschein, idLizenz)" + " values(?, ?);";

		PreparedStatement statement = dbc.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

		statement.setInt(1, deliveryNotePosition.getDeliveryNoteId());
		statement.setInt(2, deliveryNotePosition.getLicenseId());

		int results = statement.executeUpdate();
		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				deliveryNotePosition.setId(generatedKeys.getInt(1));
			} else {
				throw new SQLException("Creating license failed, no ID obtained.");
			}
		}
		System.out.println("inserted " + results + " deliveryNotePosition:" + deliveryNotePosition);
		return deliveryNotePosition;

	}
	

	public List<DeliveryNotePosition> selectAllPositionsForDeliveryNote(int deliveryNoteId) throws SQLException {
		List<DeliveryNotePosition> dnpList = new ArrayList<DeliveryNotePosition>();
		String query = "select * from lieferschein_pos where idLieferschein = ?;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		statement.setInt(1, deliveryNoteId);

		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			DeliveryNotePosition dnp = new DeliveryNotePosition(rs.getInt("idLieferschein_Pos"),
					rs.getInt("idLieferschein"), rs.getInt("idLizenz"));
			dnpList.add(dnp);
		}
		return dnpList;

	}
}
