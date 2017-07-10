package de.schneefisch.fruas.controller;

import java.sql.Date;

import de.schneefisch.fruas.database.DeliveryNoteDAO;
import de.schneefisch.fruas.model.DeliveryNote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CreateDeliveryNoteController {
	@FXML
	private DatePicker datePicker;

	@FXML
	private Button createDeliveryNoteButton;
	@FXML
	private Button cancelButton;

	@FXML
	private void createDeliveryNote(ActionEvent event) {
		System.out.println(datePicker.getValue());
		DeliveryNote dn = new DeliveryNote(Date.valueOf(datePicker.getValue()));
		try {
			DeliveryNoteDAO dnDAO = new DeliveryNoteDAO();
			dnDAO.insertDeliveryNote(dn);
		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Erstellen nicht möglich!");
			alert.setHeaderText(null);
			alert.setContentText("Der Lieferschein-Eintrag konnte nicht erstellt werden.\n");																		
			alert.showAndWait();
			Stage stage = (Stage) cancelButton.getScene().getWindow();
			stage.close();
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Lieferschein-Eintrag erstellt!");
		alert.setHeaderText(null);
		alert.setContentText("Der Lieferschein-Eintrag wurde erfolgreich erstellt.\n"); 
																	
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
