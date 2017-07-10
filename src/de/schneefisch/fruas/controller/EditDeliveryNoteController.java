package de.schneefisch.fruas.controller;

import java.sql.Date;
import java.sql.SQLException;

import de.schneefisch.fruas.database.DeliveryNoteDAO;
import de.schneefisch.fruas.model.DeliveryNote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EditDeliveryNoteController {

	@FXML private Button saveButton;
	@FXML private Button cancelButton;
	@FXML private DatePicker datePicker;
	
	private DeliveryNote deliveryNote;
	
	
	@FXML 
	private void save(ActionEvent event) {
		DeliveryNote editedNote = new DeliveryNote(deliveryNote.getId(), Date.valueOf(datePicker.getValue()));
		DeliveryNoteDAO dnDAO = new DeliveryNoteDAO();
		try {
			dnDAO.updateDeliveryNote(editedNote);
		} catch (SQLException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Änderung nicht möglich!");
			alert.setHeaderText(null);
			alert.setContentText("Der Lieferschein-Eintrag konnte nicht geändert werden.\n");																		
			alert.showAndWait();
			Stage stage = (Stage) cancelButton.getScene().getWindow();
			stage.close();
			e.printStackTrace();
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Lieferschein-Eintrag geändert!");
		alert.setHeaderText(null);
		alert.setContentText("Der Lieferschein-Eintrag wurde erfolgreich geändert.\n"); 
																	
		alert.showAndWait();
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	@FXML 
	private void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	public void setDeliveryNote(DeliveryNote deliveryNote) {
		this.deliveryNote = deliveryNote;
	}
	
	void initData() {
		datePicker.setValue(deliveryNote.getDate().toLocalDate());
	}
	
}
