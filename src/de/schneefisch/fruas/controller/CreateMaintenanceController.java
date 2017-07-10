package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.MaintenanceDAO;
import de.schneefisch.fruas.model.Maintenance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.Date;

public class CreateMaintenanceController {

    @FXML private TextField info;
    @FXML private TextField price;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;   
    @FXML private Button createMaintenanceButton;
    @FXML private Button cancelButton;

    @FXML
    private void createMaintenance(ActionEvent event) {
        Maintenance maintenance = new Maintenance(info.getText(), Float.parseFloat(price.getText().trim()), Date.valueOf(startDatePicker.getValue()), Date.valueOf(endDatePicker.getValue()));
        try {
            MaintenanceDAO pdao = new MaintenanceDAO();
            pdao.insertMaintenance(maintenance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wartungseintrag erstellt!");
		alert.setHeaderText(null);
		alert.setContentText("Neuer Wartungseintrag wurde erfolgreich erstellt!\n"); //+ insertedLicense.toStringForAlert());
		alert.showAndWait();
		Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
