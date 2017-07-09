package de.schneefisch.fruas.database;

import de.schneefisch.fruas.model.Bill;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    private DBConnector dbc;

    public BillDAO() {
        try {
            this.dbc = new DBConnector();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int updateBill(Bill bill) throws SQLException {
        String query = "update rechnung set "
                + "betragRechnung = ?, "
                + "bezahltRechnung = ?, "
                + "idLieferschein = ? "
                + "where idRechnung = ?;";
        PreparedStatement statement = dbc.getConnection().prepareStatement(query);
        statement.setFloat(1, bill.getPrice());
        statement.setBoolean(2, bill.getPaid());
        statement.setInt(3, bill.getDeliveryNoteId());
        statement.setInt(4, bill.getId());
        int updated = statement.executeUpdate();
        return updated;
    }

    public int deleteBill(int billId) throws SQLException {
        String query = "delete from rechnung where idRechnung = ?;";
        PreparedStatement statement = dbc.getConnection().prepareStatement(query);
        statement.setInt(1, billId);
        int removed = statement.executeUpdate();
        return removed;
    }

    public Bill searchBillById(int billId) throws SQLException {
        String query = "select * from rechnung where idRechnung = ?;";
        PreparedStatement statement = dbc.getConnection().prepareStatement(query);
        statement.setInt(1, billId);
        ResultSet rs = statement.executeQuery();
        Bill bill = null;
        while(rs.next()) {
            bill =  new Bill(rs.getInt("idRechnung"), rs.getBoolean("bezahltRechnung"), rs.getFloat("betragRechnung"),
                    rs.getInt("idLieferschein"));
        }
        return bill;
    }

    public Bill searchBillByDeliveryNoteId(int deliveryNoteId) throws SQLException {
        String query = "select * from rechnung where idLieferschein = ?;";
        PreparedStatement statement = dbc.getConnection().prepareStatement(query);
        statement.setInt(1, deliveryNoteId);
        ResultSet rs = statement.executeQuery();
        Bill bill = null;
        while(rs.next()) {
            bill =  new Bill(rs.getInt("idRechnung"), rs.getBoolean("bezahltRechnung"), rs.getFloat("betragRechnung"),
                    rs.getInt("idLieferschein"));
        }
        return bill;
    }

    public List<Bill> selectAllBills() throws SQLException {
        List<Bill> billList = new ArrayList<Bill>();
        String query = "select * from rechnung;";
        PreparedStatement statement = dbc.getConnection().prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            Bill bill =  new Bill(rs.getInt("idRechnung"), rs.getBoolean("bezahltRechnung"), rs.getFloat("betragRechnung"),
                    rs.getInt("idLieferschein"));
            billList.add(bill);
        }
        return billList;
    }

    public Bill insertBill(Bill bill) throws SQLException {

        String query = "insert into rechnung (bezahltRechnung, betragRechnung, "
                + "idLieferschein)" + " values(?, ?, ?);";

        PreparedStatement statement = dbc.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        statement.setBoolean(1, bill.getPaid());
        statement.setFloat(2, bill.getPrice());
        statement.setInt(3, bill.getDeliveryNoteId());

        int results = statement.executeUpdate();
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                bill.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating bill failed, no ID obtained.");
            }
        }
        System.out.println("inserted " + results +" bill:" + bill);
        return bill;
    }

}
