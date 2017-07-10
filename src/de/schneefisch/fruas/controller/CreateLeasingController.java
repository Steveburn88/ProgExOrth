package de.schneefisch.fruas.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import de.schneefisch.fruas.database.CustomerDAO;
import de.schneefisch.fruas.database.LeasingDAO;
import de.schneefisch.fruas.database.ProductDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.Leasing;
import de.schneefisch.fruas.model.Product;
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

public class CreateLeasingController implements Initializable{

	@FXML private Button createButton;
	@FXML private Button cancelButton;
	@FXML private TextField customerIdField;
	@FXML private TextField productIdField;
	@FXML private TextField billPaymentField;
	@FXML private TextField numberOfBillField;
	@FXML private DatePicker startDatePicker;
	@FXML private DatePicker firstBillDatePicker;
	@FXML private DatePicker dueBillDatePicker;
	@FXML private DatePicker recentBillDatePicker;
	@FXML private ComboBox<String> customerBox;
	@FXML private ComboBox<String> productBox;
	
	private ObservableList<String> customerList = FXCollections.observableArrayList();
	private ObservableList<String> productList = FXCollections.observableArrayList();
	
	@FXML 
	private void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	@FXML 
	private void create(ActionEvent event) {
		Leasing createdLeasing = new Leasing(Integer.valueOf(customerIdField.getText()),Integer.valueOf(productIdField.getText()), 
				Date.valueOf(startDatePicker.getValue()), Date.valueOf(firstBillDatePicker.getValue()), Date.valueOf(recentBillDatePicker.getValue()), 
				Date.valueOf(dueBillDatePicker.getValue()),  Integer.valueOf(numberOfBillField.getText()), Float.valueOf(billPaymentField.getText()));
		try {
			LeasingDAO lDAO = new LeasingDAO();
			lDAO.insertLeasing(createdLeasing);
		} catch (Exception e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Erstellen nicht möglich!");
			alert.setHeaderText(null);
			alert.setContentText("Der Leasing-Eintrag konnte nicht erstellt werden.\n"); 
																		
			alert.showAndWait();
			Stage stage = (Stage) cancelButton.getScene().getWindow();
			stage.close();
		}
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Leasing-Eintrag erstellt!");
		alert.setHeaderText(null);
		alert.setContentText("Der Leasing-Eintrag wurde erfolgreich erstellt.\n"); 
																	
		alert.showAndWait();
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CustomerDAO cDAO = new CustomerDAO();
		ProductDAO pDAO = new ProductDAO();
		try {
			List<Customer> cList = cDAO.selectAllCustomers();
			List<Product> pList = pDAO.selectAllProducts();
			System.out.println(cList);
			System.out.println(pList);
			List<String> customerStringList = cList.stream().map(c -> c.toStringForList()).collect(Collectors.toList());
			List<String> productStringList = pList.stream().map(p -> p.toStringForList()).collect(Collectors.toList());
			customerList.addAll(customerStringList);
			productList.addAll(productStringList);
			customerBox.getItems().addAll(customerList);
			productBox.getItems().addAll(productList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		customerBox.setOnAction(e -> customerIdField.setText(customerBox.getValue().substring(customerBox.getValue().indexOf("[")+1,customerBox.getValue().indexOf("]") )));
		productBox.setOnAction(e -> productIdField.setText(productBox.getValue().substring(productBox.getValue().indexOf("[")+1,productBox.getValue().indexOf("]") )));
	}
	
}
