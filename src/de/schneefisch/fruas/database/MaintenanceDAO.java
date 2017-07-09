package de.schneefisch.fruas.database;

import de.schneefisch.fruas.model.Maintenance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceDAO {

    private DBConnector dbc;

    public MaintenanceDAO() {
        try {
            this.dbc = new DBConnector();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int updateMaintenance(Maintenance maintenance) throws SQLException {
        String query = "update maintenance set "
                + "infoMaintenance = ?, "
                + "kaufpreisMaintenance = ?, "
                + "startDatumMaintenance = ?, "
                + "endDatumMaintenance = ? "
                + "where idMaintenance = ?;";
        PreparedStatement statement = dbc.getConnection().prepareStatement(query);
        statement.setString(1, maintenance.getInfo());
        statement.setFloat(2, maintenance.getPrice());
        statement.setDate(3, maintenance.getStart());
        statement.setDate(4, maintenance.getEnd());
        statement.setInt(5, maintenance.getId());
        int updated = statement.executeUpdate();
        return updated;
    }

    public int deleteMaintenance(int maintenanceId) throws SQLException {
        String query = "delete from maintenance where idMaintenance = ?;";
        PreparedStatement statement = dbc.getConnection().prepareStatement(query);
        statement.setInt(1, maintenanceId);
        int removed = statement.executeUpdate();
        return removed;
    }

    public Maintenance searchMaintenanceById(int maintenanceId) throws SQLException {
        String query = "select * from maintenance where idMaintenance = ?;";
        PreparedStatement statement = dbc.getConnection().prepareStatement(query);
        statement.setInt(1, maintenanceId);
        ResultSet rs = statement.executeQuery();
        Maintenance maintenance = new Maintenance();
        while(rs.next()) {
            maintenance =  new Maintenance(rs.getInt("idMaintenance"),
                    rs.getString("infoMaintenance"), rs.getFloat("kaufpreisMaintenance"),
                    rs.getDate("startDatumMaintenance"), rs.getDate("endDatumMaintenance"));
        }
        return maintenance;
    }

    public List<Maintenance> selectAllMaintenances() throws SQLException {
        List<Maintenance> maintenanceList = new ArrayList<Maintenance>();
        String query = "select * from maintenance;";
        PreparedStatement statement = dbc.getConnection().prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            Maintenance maintenance =  new Maintenance(rs.getInt("idMaintenance"),
                    rs.getString("infoMaintenance"), rs.getFloat("kaufpreisMaintenance"),
                    rs.getDate("startDatumMaintenance"), rs.getDate("endDatumMaintenance"));
            maintenanceList.add(maintenance);
        }
        return maintenanceList;
    }

    public Maintenance insertMaintenance(Maintenance maintenance) throws SQLException {

        String query = "insert into maintenance (infoMaintenance, kaufpreisMaintenance, "
                + "startDatumMaintenance, endDatumMaintenance)" + " values(?, ?, ?, ?);";

        PreparedStatement statement = dbc.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, maintenance.getInfo());
        statement.setFloat(2, maintenance.getPrice());
        statement.setDate(3, maintenance.getStart());
        statement.setDate(4, maintenance.getEnd());

        int results = statement.executeUpdate();
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                maintenance.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating maintenance failed, no ID obtained.");
            }
        }
        System.out.println("inserted " + results +" maintenance:" + maintenance);
        return maintenance;
    }

}
