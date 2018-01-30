package de.schneefisch.fruas.controller;

import java.sql.SQLException;

import de.schneefisch.fruas.database.LocationDAO;
import de.schneefisch.fruas.model.FiCustomer;
import de.schneefisch.fruas.model.Location;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EditLocationController {
	@FXML private Button saveButton;
	@FXML private Button cancelButton;
	
	@FXML private TextField postalCodeField;
	@FXML private TextField cityField;
	@FXML private TextField postBoxField;
	@FXML private TextField streetField;
	@FXML private TextField houseNumberField;
	
	private FiCustomer fiCustomer;
	private Location location;
	
	public void initData() {
		postalCodeField.setText(location.getPostalCode());
		cityField.setText(location.getCity());
		postBoxField.setText(location.getPostBox());
		streetField.setText(location.getStreet());
		houseNumberField.setText(location.getHouseNumber());
		
	}
	
	@FXML private void save(ActionEvent event) {
		Location edited = new Location(location.getId(), fiCustomer.getId(), postalCodeField.getText(), cityField.getText(), postBoxField.getText(), streetField.getText(), houseNumberField.getText());
		LocationDAO lDAO = new LocationDAO();
		int updated= 0;
		try {
			updated = lDAO.updateLocation(edited);
			
		} catch (SQLException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Achtung!");
			alert.setHeaderText(null);
			alert.setContentText("Standort konnte nicht erstellt werden!");
			alert.showAndWait();
			Stage stage = (Stage) cancelButton.getScene().getWindow();
			stage.close();
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Standort erstellt!");
		alert.setHeaderText(null);
		alert.setContentText("Der neue Standort wurde erfolgreich erstellt.");
		alert.showAndWait();
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	@FXML private void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	public void setFiCustomer(FiCustomer fiCustomer) {
		this.fiCustomer = fiCustomer;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}

	
}
