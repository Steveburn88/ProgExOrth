package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.LicenseDAO;
import de.schneefisch.fruas.model.License;
import de.schneefisch.fruas.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CreateLicenseController {

	@FXML
	private Button cancelButton;
	@FXML
	private Button createButton;

	@FXML
	private TextField productIdField;
	@FXML
	private TextField keyField;

	@FXML
	private void createLicense() {
		License license = new License(Integer.valueOf(productIdField.getText()), keyField.getText());
		License insertedLicense = null;
		try {
			LicenseDAO lDAO = new LicenseDAO();
			insertedLicense = lDAO.insertLicense(license);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Lizenz erstellt!");
		alert.setHeaderText(null);
		alert.setContentText("Neue Lizenz:\n"); //+ insertedLicense.toStringForAlert());
		alert.showAndWait();
		
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	public void setProductId(Product product) {
		productIdField.setText(Integer.toString(product.getId()));
		productIdField.setEditable(false);
	}

	@FXML
	private void cancel() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
}
