package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.CustomerDAO;
import de.schneefisch.fruas.database.FiCustomerDAO;
import de.schneefisch.fruas.database.LocationDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.FiCustomer;
import de.schneefisch.fruas.model.Location;
import de.schneefisch.fruas.model.Salutation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.xml.ws.Action;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class CreateCustomerController implements Initializable{
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
	private ComboBox<String> fiCustomerBox;
	
	private ObservableList<String> fiCustomerList = FXCollections.observableArrayList();

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
			Customer insertedCustomer = null;
			int fiCustomerIdAsInt = Integer.parseInt(fiCustomerId.getText().trim(), 10);
			try {
				CustomerDAO cdao = new CustomerDAO();
				LocationDAO ldao = new LocationDAO();				
				Location location = ldao.findLocationByFiCustomerId(fiCustomerIdAsInt);
				if(location != null) {
					Customer customer = new Customer(fiCustomerIdAsInt, location.getId(), salut, firstName.getText(),
							lastName.getText(), phoneNumber.getText(), email.getText(), position.getText(),
							department.getText(), roomNumber.getText(), buildingNumber.getText(), faxNumber.getText());
					insertedCustomer = cdao.insertCustomer(customer);
				} else {
					System.out.println("es gibt keinen standort mit dieser fikuid");
					return;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Kunde erstellt!");
			alert.setHeaderText(null);
			alert.setContentText("Neuer Kunde:\n" + insertedCustomer.toStringForAlert());
			alert.showAndWait();
			Stage stage = (Stage) cancelButton.getScene().getWindow();
			stage.close();
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
			System.out.println("Fehler beim Oeffnen des Neuer FiKunde Fensters!");
			e.printStackTrace();
		}
		CreateFiCustomerController controller = loader.<CreateFiCustomerController>getController();
		controller.initData(customer);
		stage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		FiCustomerDAO fcDAO = new FiCustomerDAO();
		try {
			List<FiCustomer> fcList = fcDAO.selectAllFiCustomers();
			System.out.println(fcList);
			List<String> stringList = fcList.stream().map(fc -> fc.toStringForList()).collect(Collectors.toList());
			fiCustomerList.addAll(stringList);
			fiCustomerBox.getItems().addAll(fiCustomerList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		fiCustomerBox.setOnAction(e -> fiCustomerId.setText(fiCustomerBox.getValue().substring(fiCustomerBox.getValue().indexOf("[")+1,fiCustomerBox.getValue().indexOf("]") )));		
	}
}
