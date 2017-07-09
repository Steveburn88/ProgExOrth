package de.schneefisch.fruas.controller;

import java.sql.Date;

import de.schneefisch.fruas.database.OfferDAO;
import de.schneefisch.fruas.model.Offer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EditOfferController {
	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;
	@FXML
	private TextField customerIdField;
	@FXML
	private TextField validityField;
	@FXML
	private Offer offer;

	@FXML
	private void save() {
		Offer updatedOffer = new Offer(this.offer.getId(), Integer.valueOf(customerIdField.getText()),
				Date.valueOf(validityField.getText()));

		try {
			OfferDAO oDAO = new OfferDAO();
			int updated = oDAO.updateOffer(updatedOffer);
			System.out.println("affected rows: " + updated);
		} catch (Exception e) {
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

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	private void printOkAndCloseStage() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Angebot geändert!");
		alert.setHeaderText(null);
		alert.setContentText("Der bestehende Eintrag wurde erfolgreich aktualisiert!\n");
		alert.showAndWait();
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	private void printErrorAndCloseStage() {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Angebot nicht geändert!");
		alert.setHeaderText(null);
		alert.setContentText("Der bestehende Eintrag konnte nicht verändert werden!\n");
		alert.showAndWait();
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	public void initTextFields() {
		customerIdField.setText(String.valueOf(this.offer.getCustomerId()));
		validityField.setText(String.valueOf(this.offer.getValidity()));		
	}

}
