package de.schneefisch.fruas.controller;

import java.net.URL;
import java.util.ResourceBundle;

import de.schneefisch.fruas.model.License;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

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
