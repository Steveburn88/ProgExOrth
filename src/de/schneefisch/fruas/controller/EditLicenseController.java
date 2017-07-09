
package de.schneefisch.fruas.controller;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import de.schneefisch.fruas.database.CustomerDAO;
import de.schneefisch.fruas.database.LicenseDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.License;
import de.schneefisch.fruas.model.Salutation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
	    private TextField keyField;
	    @FXML
	    private TextField discountField;
	    @FXML
	    private TextField soldField;
	    @FXML
	    private TextField endField;
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
	    
	    @FXML
	    private void saveLicense(ActionEvent event) {
	    	boolean sold;
	    	if(soldRadioButton.isSelected()) {
	    		sold = true;
	    	} else sold = false;
	    	Date soldD = null;
	    	Date endD = null;
	    	if(!soldField.getText().isEmpty()) {
	    		soldD = Date.valueOf(soldField.getText());
	    	}
	    	if(!endField.getText().isEmpty()) {
	    		endD = Date.valueOf(endField.getText());
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
			// TODO Auto-generated method stub
			
		}
		public void setEditableLicense(License getsEdited) {
			this.license = getsEdited;
			customerIdField.setText(Integer.toString(getsEdited.getCustomerId()));
			productIdField.setText(Integer.toString(getsEdited.getProductId()));
			keyField.setText(getsEdited.getKey());
			
			discountField.setText(Float.toString(getsEdited.getDiscount()));
			if(getsEdited.getSoldDate() != null) {
				soldField.setText(getsEdited.getSoldDate().toString());
			}
			if(getsEdited.getEndDate() != null) {
				endField.setText(getsEdited.getEndDate().toString());
			}
			
			maintenanceIdField.setText(Integer.toString(getsEdited.getMaintenanceId()));
			if(getsEdited.isSold()) {
				soldRadioButton.setSelected(true);
			} else notSoldRadioButton.setSelected(true);
			
		}
	    
}

