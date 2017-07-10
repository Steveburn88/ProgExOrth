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

public class EditMaintenanceController {

	@FXML private Button saveButton;
	@FXML private Button cancelButton;
	@FXML private TextField info;
	@FXML private TextField price;
	@FXML private DatePicker startDatePicker;
	@FXML private DatePicker endDatePicker;
	@FXML private Maintenance maintenance;

	

	void initData(Maintenance maintenance) {
		this.maintenance = maintenance;
		System.out.println(maintenance.getId());
		info.setText(String.valueOf(maintenance.getInfo()));
		price.setText(String.valueOf(maintenance.getPrice()));
		startDatePicker.setValue(maintenance.getStart().toLocalDate());
		endDatePicker.setValue(maintenance.getEnd().toLocalDate());
	}

	@FXML
    private void saveMaintenance() {
        Maintenance updatedMaintenance = new Maintenance(maintenance.getId(), info.getText(),
                Float.parseFloat(price.getText().trim()), Date.valueOf(startDatePicker.getValue()), Date.valueOf(endDatePicker.getValue()));

        try {
            MaintenanceDAO mdao = new MaintenanceDAO();
            int updated = mdao.updateMaintenance(updatedMaintenance);
            System.out.println("affected rows: "+ updated);
        } catch ( Exception e) {
            e.printStackTrace();
            printErrorAndCloseStage();
        }
        printOkAndCloseStage();
        
    }

	@FXML
	private void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	private void printOkAndCloseStage() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wartungseintrag geändert!");
		alert.setHeaderText(null);
		alert.setContentText("Der bestehende Eintrag wurde erfolgreich aktualisiert!\n");
		alert.showAndWait();
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	private void printErrorAndCloseStage() {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Wartungseintrag nicht geändert!");
		alert.setHeaderText(null);
		alert.setContentText("Der bestehende Eintrag konnte nicht verändert werden!\n");
		alert.showAndWait();
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

}
