package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.DBConnector;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.FiCustomer;
import de.schneefisch.fruas.model.Location;
import de.schneefisch.fruas.model.Salutation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateFiCustomerController {
	
	@FXML private TextField name;
	@FXML private TextField postCode;
	@FXML private TextField city;
	@FXML private TextField postBox;
	@FXML private TextField street;
	@FXML private TextField houseNumber;
	@FXML private Button createFiCustomerButton;	
	@FXML private Button cancelButton;	
	@FXML private Customer customer;

	
	void initialize(){};
	void initData(Customer customer) {
		this.customer = customer;		
	}
	
	@FXML
	private void createFiCustomer(ActionEvent event) throws Exception {								
		FiCustomer fiCustomer = new FiCustomer(name.getText());
		Location location = new Location(postCode.getText(), city.getText(), postBox.getText(), street.getText(), houseNumber.getText());
		System.out.println("Customer created:");
		System.out.println(customer);
		System.out.println("FiCustomer created:");
		System.out.println(fiCustomer);	
		System.out.println("Location created:");
		System.out.println(location);
		
		DBConnector dbc = new DBConnector();
		
		FiCustomer insertedFi = dbc.insertFiCustomer(fiCustomer);
		location.setFiKuId(insertedFi.getId());
		Location insertedLoc = dbc.insertLocation(location);
		customer.setFiKuId(insertedFi.getId());
		customer.setLocationId(insertedLoc.getId());
		Customer insertedCust = dbc.insertCustomer(customer);
		
	}
	
	@FXML
	private void cancel(ActionEvent event) {
	    Stage stage = (Stage) cancelButton.getScene().getWindow();
	    stage.close();
	}
	
	
}
