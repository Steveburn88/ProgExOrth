package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.MaintenanceDAO;
import de.schneefisch.fruas.model.Maintenance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;

public class CreateMaintenanceController {

    @FXML
    private TextField info;
    @FXML
    private TextField price;
    @FXML
    private TextField start;
    @FXML
    private TextField end;
    @FXML
    private Button createMaintenanceButton;
    @FXML
    private Button cancelButton;

    @FXML
    private void createMaintenance(ActionEvent event) {
        Maintenance maintenance = new Maintenance(info.getText(), Float.parseFloat(price.getText().trim()), Date.valueOf(start.getText()), Date.valueOf(end.getText()));
        try {
            MaintenanceDAO pdao = new MaintenanceDAO();
            pdao.insertMaintenance(maintenance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
