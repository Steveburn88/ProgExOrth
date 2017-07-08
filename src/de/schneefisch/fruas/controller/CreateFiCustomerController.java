package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.CustomerDAO;
import de.schneefisch.fruas.database.FiCustomerDAO;
import de.schneefisch.fruas.database.LocationDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.FiCustomer;
import de.schneefisch.fruas.model.Location;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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
		CustomerDAO cdao = new CustomerDAO();
		FiCustomerDAO fcdao = new FiCustomerDAO();
		LocationDAO ldao = new LocationDAO();
		
		FiCustomer insertedFi = fcdao.insertFiCustomer(fiCustomer);
		location.setFiKuId(insertedFi.getId());
		Location insertedLoc = ldao.insertLocation(location);
		customer.setFiKuId(insertedFi.getId());
		customer.setLocationId(insertedLoc.getId());
		Customer insertedCustomer = cdao.insertCustomer(customer);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Kunde erstellt!");
		alert.setHeaderText(null);
		alert.setContentText("Neuer Kunde: " + "\n" + insertedCustomer.toStringForAlert() + "\n"
				 + "\n"+ "Neuer Firmenkunde: " + "\n" + insertedFi.toStringForAlert() + "\n" 
				 + "\n"+ "Neuer Standort: " + "\n" + insertedLoc.toStringForAlert());
		alert.showAndWait();
		
		
		Stage stage = (Stage) createFiCustomerButton.getScene().getWindow();
	    stage.close();
		
	}
	
	@FXML
	private void cancel(ActionEvent event) {
	    Stage stage = (Stage) cancelButton.getScene().getWindow();
	    stage.close();
	}
	
	
}
