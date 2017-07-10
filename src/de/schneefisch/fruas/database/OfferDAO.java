package de.schneefisch.fruas.database;

import de.schneefisch.fruas.model.Offer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.Maintenance;
import de.schneefisch.fruas.model.Offer;
import de.schneefisch.fruas.model.Product;
import de.schneefisch.fruas.model.Salutation;

public class OfferDAO {

	private DBConnector dbc;

	public OfferDAO() {
		try {
			this.dbc = new DBConnector();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int deleteOffer(int id) throws SQLException {
		String query = "delete from angebot where idAngebot = ?;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		statement.setInt(1, id);
		int removed = statement.executeUpdate();
		return removed;
	}

	public List<Offer> findOffersByCustomerId(int customerId) throws SQLException {
		String query = "select * from angebot where idPersonenkunde = ?;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		statement.setInt(1, customerId);
		ResultSet rs = statement.executeQuery();
		List<Offer> offerList = new ArrayList<Offer>();
		while (rs.next()) {
			Offer offer = new Offer(rs.getInt("idAngebot"), rs.getInt("idPersonenkunde"),
					rs.getDate("gueltigkeitAngebot"));
			offerList.add(offer);
		}
		return offerList;
	}

	public Offer searchOfferById(int offerId) throws SQLException {
		String query = "select * from angebot where idAngebot = ?;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		statement.setInt(1, offerId);
		ResultSet rs = statement.executeQuery();
		Offer offer = null;
		while (rs.next()) {
			offer = new Offer(rs.getInt("idAngebot"), rs.getInt("idPersonenkunde"), rs.getDate("gueltigkeitAngebot"));
		}
		return offer;
	}

	public List<Offer> selectAllOffers() throws SQLException {
		List<Offer> offerList = new ArrayList<Offer>();
		String query = "select * from angebot;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			Offer offer = new Offer(rs.getInt("idAngebot"), rs.getInt("idPersonenkunde"),
					rs.getDate("gueltigkeitAngebot"));
			offerList.add(offer);
		}
		return offerList;
	}

	public Offer insertOffer(Offer offer) throws SQLException {

		String query = "insert into angebot (idPersonenkunde, gueltigkeitAngebot) " + " values(?, ?);";

		PreparedStatement statement = dbc.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

		statement.setInt(1, offer.getCustomerId());
		statement.setDate(2, offer.getValidity());

		int results = statement.executeUpdate();
		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				offer.setId(generatedKeys.getInt(1));
			} else {
				throw new SQLException("Creating offer failed, no ID obtained.");
			}
		}
		System.out.println("inserted " + results + " offer:" + offer);
		return offer;
	}

	public int updateOffer(Offer offer) throws SQLException {
		String query = "update angebot set idPersonenkunde = ?, " + "gueltigkeitAngebot = ? " + "where idAngebot = ?;";
		PreparedStatement statement = dbc.getConnection().prepareStatement(query);

		statement.setInt(1, offer.getCustomerId());
		statement.setDate(2, offer.getValidity());
		statement.setInt(3, offer.getId());

		int updated = statement.executeUpdate();
		return updated;

	}
}
