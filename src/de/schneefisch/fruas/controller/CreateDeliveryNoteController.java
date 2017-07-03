package de.schneefisch.fruas.controller;

import java.sql.Date;

import de.schneefisch.fruas.database.DeliveryNoteDAO;
import de.schneefisch.fruas.model.DeliveryNote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateDeliveryNoteController {
	@FXML
	private TextField date;

	@FXML
	private Button createDeliveryNoteButton;
	@FXML
	private Button cancelButton;

	@FXML
	private void createDeliveryNote(ActionEvent event) {
		System.out.println(date.getText());
		DeliveryNote dn = new DeliveryNote(Date.valueOf(date.getText()));
		try {
			DeliveryNoteDAO dnDAO = new DeliveryNoteDAO();
			dnDAO.insertDeliveryNote(dn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
}
