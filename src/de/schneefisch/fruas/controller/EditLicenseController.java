
package de.schneefisch.fruas.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import de.schneefisch.fruas.database.CustomerDAO;
import de.schneefisch.fruas.database.LicenseDAO;
import de.schneefisch.fruas.database.MaintenanceDAO;
import de.schneefisch.fruas.database.ProductDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.License;
import de.schneefisch.fruas.model.Maintenance;
import de.schneefisch.fruas.model.Product;
import de.schneefisch.fruas.model.Salutation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EditLicenseController implements Initializable {

	 	@FXML
	    private Button saveButton;
	    @FXML
	    private Button cancelButton;
	    @FXML
	    private TextField customerIdField;
	    @FXML
	    private TextField productIdField;
	    @FXML
	    private ComboBox<String> customerBox;
	    @FXML
	    private ComboBox<String> productBox;
	    @FXML
	    private ComboBox<String> maintenanceBox;
	    @FXML
	    private TextField keyField;
	    @FXML
	    private TextField discountField;
	    @FXML
	    private DatePicker soldDatePicker;
	    @FXML 
	    private DatePicker endDatePicker;	    
	    @FXML
	    private TextField maintenanceIdField;
	    @FXML 
	    private RadioButton soldRadioButton;
	    @FXML 
	    private RadioButton notSoldRadioButton;
	    @FXML
		private ToggleGroup soldGroup;
	    @FXML
	    private License license;
	    
	    private ObservableList<String> customerList = FXCollections.observableArrayList();
		private ObservableList<String> productList = FXCollections.observableArrayList();
		private ObservableList<String> maintenanceList = FXCollections.observableArrayList();
		
	    @FXML
	    private void saveLicense(ActionEvent event) {
	    	boolean sold;
	    	if(soldRadioButton.isSelected()) {
	    		sold = true;
	    	} else sold = false;
	    	Date soldD = null;
	    	Date endD = null;
	    	if(!(soldDatePicker.getValue() == null)) {
	    		soldD = Date.valueOf(soldDatePicker.getValue());
	    	}
	    	if(!(endDatePicker.getValue() == null)) {
	    		endD = Date.valueOf(endDatePicker.getValue());
	    	}
	    	
	    	License license = new License(this.license.getId(), Integer.valueOf(customerIdField.getText()), Integer.valueOf(productIdField.getText()), keyField.getText(),  sold, 
	    			Float.valueOf(discountField.getText()), soldD, endD, Integer.valueOf(maintenanceIdField.getText()));			
			int updated= 0;
			try {
				LicenseDAO lDAO = new LicenseDAO();
				updated = lDAO.updateLicense(license);
				System.out.println("affected rows: "+ updated);
			} catch ( Exception e) {
				e.printStackTrace();
			}
			if(updated == 1) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Lizenz aktualisiert!");
				alert.setHeaderText(null);
				alert.setContentText("Neue Informationen:\n" + license.toStringForAlert());
				alert.showAndWait();
				Stage stage = (Stage) cancelButton.getScene().getWindow();
				stage.close();
			}
	    }
	    @FXML
	    private void cancel(ActionEvent event) {
	    	
	    }
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			CustomerDAO cDAO = new CustomerDAO();
			ProductDAO pDAO = new ProductDAO();
			MaintenanceDAO mDAO = new MaintenanceDAO();
			try {
				List<Customer> cList = cDAO.selectAllCustomers();
				List<Product> pList = pDAO.selectAllProducts();
				List<Maintenance> mList = mDAO.selectAllMaintenances();
				System.out.println(cList);
				System.out.println(pList);
				System.out.println(mList);
				List<String> customerStringList = cList.stream().map(c -> c.toStringForList()).collect(Collectors.toList());
				List<String> productStringList = pList.stream().map(p -> p.toStringForList()).collect(Collectors.toList());
				List<String> maintenanceStringList = mList.stream().map(p -> p.toStringForList()).collect(Collectors.toList());
				customerList.addAll(customerStringList);
				productList.addAll(productStringList);
				maintenanceList.addAll(maintenanceStringList);
				customerBox.getItems().addAll(customerList);
				productBox.getItems().addAll(productList);
				maintenanceBox.getItems().addAll(maintenanceList);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			customerBox.setOnAction(e -> customerIdField.setText(customerBox.getValue().substring(customerBox.getValue().indexOf("[")+1,customerBox.getValue().indexOf("]") )));
			productBox.setOnAction(e -> productIdField.setText(productBox.getValue().substring(productBox.getValue().indexOf("[")+1,productBox.getValue().indexOf("]") )));
			maintenanceBox.setOnAction(e -> maintenanceIdField.setText(maintenanceBox.getValue().substring(maintenanceBox.getValue().indexOf("[")+1,maintenanceBox.getValue().indexOf("]") )));
			
		}
		public void setEditableLicense(License getsEdited) {
			this.license = getsEdited;
			customerIdField.setText(Integer.toString(getsEdited.getCustomerId()));
			productIdField.setText(Integer.toString(getsEdited.getProductId()));
			keyField.setText(getsEdited.getKey());
			
			discountField.setText(Float.toString(getsEdited.getDiscount()));
			if(getsEdited.getSoldDate() != null) {
				soldDatePicker.setValue(getsEdited.getSoldDate().toLocalDate());
			}
			if(getsEdited.getEndDate() != null) {
				endDatePicker.setValue(getsEdited.getEndDate().toLocalDate());
			}
			
			maintenanceIdField.setText(Integer.toString(getsEdited.getMaintenanceId()));
			if(getsEdited.isSold()) {
				soldRadioButton.setSelected(true);
			} else notSoldRadioButton.setSelected(true);
			
		}
	    
}

