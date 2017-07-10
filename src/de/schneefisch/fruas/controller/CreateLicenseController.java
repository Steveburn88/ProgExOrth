package de.schneefisch.fruas.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import de.schneefisch.fruas.database.CustomerDAO;
import de.schneefisch.fruas.database.LicenseDAO;
import de.schneefisch.fruas.database.ProductDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.License;
import de.schneefisch.fruas.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CreateLicenseController implements Initializable{

	@FXML
	private Button cancelButton;
	@FXML
	private Button createButton;
	@FXML
	private ComboBox<String> productBox;
	
	@FXML
	private TextField productIdField;
	@FXML
	private TextField keyField;
	private ObservableList<String> productList = FXCollections.observableArrayList();
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
		alert.setContentText("Neue Lizenz wurde erfolgreich erstellt!\n"); //+ insertedLicense.toStringForAlert());
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ProductDAO pDAO = new ProductDAO();
		try {
			List<Product> pList = pDAO.selectAllProducts();
			System.out.println(pList);
			List<String> productStringList = pList.stream().map(p -> p.toStringForList()).collect(Collectors.toList());
			productList.addAll(productStringList);
			productBox.getItems().addAll(productList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		productBox.setOnAction(e -> productIdField.setText(productBox.getValue().substring(productBox.getValue().indexOf("[")+1,productBox.getValue().indexOf("]") )));
	}
}
