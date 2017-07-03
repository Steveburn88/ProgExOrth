package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.LicenseDAO;
import de.schneefisch.fruas.model.License;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateLicenseController {

	@FXML private Button cancelButton;
	@FXML private Button createButton;
	
	@FXML private TextField productIdField;
	@FXML private TextField keyField;
	
	@FXML 
	private void createLicense() {
		License license = new License(Integer.valueOf(productIdField.getText()), keyField.getText());
		  try {
	            LicenseDAO lDAO = new LicenseDAO();
	            lDAO.insertLicense(license);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	@FXML 
	private void cancel() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
}
