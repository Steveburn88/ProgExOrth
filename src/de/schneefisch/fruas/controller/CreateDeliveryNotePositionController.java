package de.schneefisch.fruas.controller;

import java.sql.SQLException;

import de.schneefisch.fruas.database.DeliveryNotePositionDAO;
import de.schneefisch.fruas.model.DeliveryNote;
import de.schneefisch.fruas.model.DeliveryNotePosition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateDeliveryNotePositionController {

	@FXML private TextField licenseIdField;
	@FXML private Button createDeliveryNoteButton;
	@FXML private Button cancelButton;
	@FXML private DeliveryNote deliveryNote;
	
	
	@FXML private void createDeliveryNotePosition(ActionEvent event) {
		DeliveryNotePosition dnp = new DeliveryNotePosition(deliveryNote.getId() ,Integer.valueOf(licenseIdField.getText()));
		
		DeliveryNotePositionDAO dnpDAO =  new DeliveryNotePositionDAO();
		try {
			dnpDAO.insertDeliveryNotePosition(dnp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 Stage stage = (Stage) cancelButton.getScene().getWindow();
		    stage.close();	
	}
	
	@FXML private void cancel(ActionEvent event) {
		 Stage stage = (Stage) cancelButton.getScene().getWindow();
		    stage.close();
	}

	
	public void initData(DeliveryNote deliveryNote) {
		this.deliveryNote = deliveryNote;
		
	}
}
