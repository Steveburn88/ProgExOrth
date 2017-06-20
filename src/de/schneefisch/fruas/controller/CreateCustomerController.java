package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.CustomerDAO;
import de.schneefisch.fruas.database.LocationDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.Location;
import de.schneefisch.fruas.model.Salutation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateCustomerController {
	@FXML
	private ToggleGroup salutation;
	@FXML
	private RadioButton herr;
	@FXML
	private RadioButton frau;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private TextField phoneNumber;
	@FXML
	private TextField position;
	@FXML
	private TextField department;
	@FXML
	private TextField email;
	@FXML
	private TextField roomNumber;
	@FXML
	private TextField buildingNumber;
	@FXML
	private TextField faxNumber;
	@FXML
	private Button createCustomerButton;
	@FXML
	private Button cancelButton;
	@FXML
	private TextField fiCustomerId;

	@FXML
	private void createCustomer(ActionEvent event) {
		Salutation salut = Salutation.Herr;
		if (frau.isSelected()) {
			salut = Salutation.Frau;
		}

		// Erstelle neuen FiKu falls keine ID angegeben wird:
		if (fiCustomerId.getText().equals("")) {
			Customer customer = new Customer(salut, firstName.getText(), lastName.getText(), phoneNumber.getText(),
					email.getText(), position.getText(), department.getText(), roomNumber.getText(),
					buildingNumber.getText(), faxNumber.getText());
			showCreateFiCustomer(customer);
			Stage stage = (Stage) cancelButton.getScene().getWindow();
			stage.close();
		} else {

			int fiCustomerIdAsInt = Integer.parseInt(fiCustomerId.getText().trim(), 10);
			try {
				CustomerDAO cdao = new CustomerDAO();
				LocationDAO ldao = new LocationDAO();				
				Location location = ldao.findLocationByFiCustomerId(fiCustomerIdAsInt);
				if(location != null) {
					Customer customer = new Customer(fiCustomerIdAsInt, location.getId(), salut, firstName.getText(),
							lastName.getText(), phoneNumber.getText(), email.getText(), position.getText(),
							department.getText(), roomNumber.getText(), buildingNumber.getText(), faxNumber.getText());
					cdao.insertCustomer(customer);
				} else {
					System.out.println("es gibt keinen standort mit dieser fikuid");
					return;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	private void showCreateFiCustomer(Customer customer) {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("createFiCustomer.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Neuer FiKu");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim öffnen des Neuer FiKunde Fensters!");
			e.printStackTrace();
		}
		CreateFiCustomerController controller = loader.<CreateFiCustomerController>getController();
		controller.initData(customer);
		stage.show();
	}
}
