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

public class EditLeasingController implements Initializable {
	
	@FXML private Button saveButton;
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
	@FXML private Leasing leasing;
	
	private ObservableList<String> customerList = FXCollections.observableArrayList();
	private ObservableList<String> productList = FXCollections.observableArrayList();
	
	public void setLeasing(Leasing leasing) {
		this.leasing = leasing;
	}
	
	public void initData() {
		customerIdField.setText(String.valueOf(leasing.getCustomerId()));
		productIdField.setText(String.valueOf(leasing.getProductId()));
		billPaymentField.setText(String.valueOf(leasing.getBillPayment()));
		numberOfBillField.setText(String.valueOf(leasing.getNumberOfBills()));
		startDatePicker.setValue(leasing.getStartDate().toLocalDate());
		firstBillDatePicker.setValue(leasing.getFirstBillDate().toLocalDate());
		dueBillDatePicker.setValue(leasing.getDueBillDate().toLocalDate());
		recentBillDatePicker.setValue(leasing.getRecentBillDate().toLocalDate());
	}
	
	@FXML 
	private void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	@FXML 
	private void save(ActionEvent event) {
		Leasing editedLeasing = new Leasing(leasing.getId(), Integer.valueOf(customerIdField.getText()),Integer.valueOf(productIdField.getText()), 
				Date.valueOf(startDatePicker.getValue()), Date.valueOf(firstBillDatePicker.getValue()), Date.valueOf(recentBillDatePicker.getValue()), 
				Date.valueOf(dueBillDatePicker.getValue()),  Integer.valueOf(numberOfBillField.getText()), Float.valueOf(billPaymentField.getText()));
		int updated= 0;
		try {
			LeasingDAO lDAO = new LeasingDAO();
			updated = lDAO.updateLeasing(editedLeasing);
			System.out.println("affected rows: "+ updated);
		} catch ( Exception e) {
			e.printStackTrace();
		}
		if(updated == 1) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Leasing aktualisiert!");
			alert.setHeaderText(null);
			alert.setContentText("Die neuen Informationen wurden erfolgreich gespeichert.");
			alert.showAndWait();
			Stage stage = (Stage) cancelButton.getScene().getWindow();
			stage.close();
		}
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
