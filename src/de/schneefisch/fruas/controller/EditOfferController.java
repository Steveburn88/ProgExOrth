package de.schneefisch.fruas.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import de.schneefisch.fruas.database.CustomerDAO;
import de.schneefisch.fruas.database.OfferDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.Offer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EditOfferController implements Initializable{
	@FXML private Button saveButton;
	@FXML private Button cancelButton;
	@FXML private TextField customerIdField;
	@FXML private DatePicker validityDatePicker;
	@FXML private ComboBox<String> customerBox;    
	@FXML private Offer offer;
	
	private ObservableList<String> customerList = FXCollections.observableArrayList();

	@FXML
	private void save() {
		Offer updatedOffer = new Offer(this.offer.getId(), Integer.valueOf(customerIdField.getText()),
				Date.valueOf(validityDatePicker.getValue()));

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
		customerIdField.setText(String.valueOf(offer.getCustomerId()));
		validityDatePicker.setValue(offer.getValidity().toLocalDate());		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CustomerDAO cDAO = new CustomerDAO();
		
		try {
			List<Customer> cList = cDAO.selectAllCustomers();
			List<String> customerStringList = cList.stream().map(c -> c.toStringForList()).collect(Collectors.toList());
			customerList.addAll(customerStringList);
			customerBox.getItems().addAll(customerList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		customerBox.setOnAction(e -> customerIdField.setText(customerBox.getValue().substring(customerBox.getValue().indexOf("[")+1,customerBox.getValue().indexOf("]") )));
	}

}
