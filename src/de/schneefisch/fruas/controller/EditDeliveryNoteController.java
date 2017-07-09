package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.model.DeliveryNote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EditDeliveryNoteController {

	@FXML private Button saveButton;
	@FXML private Button cancelButton;
	@FXML private TextField dateField;
	
	private DeliveryNote deliveryNote;
	
	
	@FXML 
	private void save(ActionEvent event) {
		
	}
	@FXML 
	private void cancel(ActionEvent event) {
		
	}
	
	public void setDeliveryNote(DeliveryNote deliveryNote) {
		this.deliveryNote = deliveryNote;
	}
	
	void initData() {
		dateField.setText(deliveryNote.getDate().toString());
	}
	
}
