package de.schneefisch.fruas.controller;

import java.sql.SQLException;

import de.schneefisch.fruas.database.FiCustomerDAO;
import de.schneefisch.fruas.model.FiCustomer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EditFiCustomerController {

	@FXML private Button saveButton;
	@FXML private Button cancelButton;
	
	@FXML private TextField nameField;
	
	@FXML private FiCustomer fiCustomer;
	
	@FXML
	private void save(ActionEvent event) {
		FiCustomer edited = new FiCustomer(fiCustomer.getId(), nameField.getText());
		FiCustomerDAO fDAO = new FiCustomerDAO();
		try {
			fDAO.updateFiCustomer(edited);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Kundendaten aktualisiert!");
			alert.setHeaderText(null);
			alert.setContentText("Informationen wurden erfolgreich gespeichert.");
			alert.showAndWait();
			Stage stage = (Stage) cancelButton.getScene().getWindow();
			stage.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	public void setFiCustomer(FiCustomer fiCustomer) {
		this.fiCustomer = fiCustomer;
	}
	
	public void initData() {
		nameField.setText(fiCustomer.getName());
	}
	
}
