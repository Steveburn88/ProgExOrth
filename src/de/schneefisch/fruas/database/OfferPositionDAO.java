package de.schneefisch.fruas.database;

import de.schneefisch.fruas.model.OfferPosition;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OfferPositionDAO {

	private DBConnector dbc;

	public OfferPositionDAO() {
		try {
			this.dbc = new DBConnector();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public OfferPosition insertOfferPosition(OfferPosition offerPosition) throws SQLException {

        String query = "insert into angebot_pos (idAngebot, anzahlAngebot_Pos, idProdukt)" +
        " values(?, ?, ?);";

        PreparedStatement statement = dbc.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        statement.setInt(1, offerPosition.getOfferId());
        statement.setInt(2, offerPosition.getCount());
        statement.setInt(3,  offerPosition.getProductId());       

        int results = statement.executeUpdate();
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
            	offerPosition.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating product failed, no ID obtained.");
            }
        }
        System.out.println("inserted " + results +" offerPosition:" + offerPosition);
        return offerPosition;
    }

	public List<OfferPosition> searchOfferPositionsByOfferId(int id) throws SQLException {
		List<OfferPosition> offerPositions = new ArrayList<OfferPosition>();
		String query = "select * from angebot_pos where idAngebot = ?";
		
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		statement.setInt(1, id);
		
		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			OfferPosition op = new OfferPosition(rs.getInt("idAngebot_Pos"), rs.getInt("idAngebot"),
					rs.getInt("anzahlAngebot_Pos"), rs.getInt("idProdukt"));
			
			offerPositions.add(op);
			
		}
		return offerPositions;
	}
	
}
