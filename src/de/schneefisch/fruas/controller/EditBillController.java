package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.BillDAO;
import de.schneefisch.fruas.model.Bill;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.sql.Date;

public class EditBillController {

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private RadioButton paidRadioButton;
    @FXML
    private RadioButton notPaidRadioButton;
    @FXML
    private ToggleGroup paidGroup;
    @FXML
    private TextField price;
    @FXML
    private TextField deliveryNoteID;
    @FXML
    private Bill bill;

    void initialize() {
    };

    void initData(Bill bill) {
        this.bill = bill;
        System.out.println(bill.getId());
        if(bill.getPaid()) {
        	paidRadioButton.setSelected(true);
        } else notPaidRadioButton.setSelected(true);        
        price.setText(String.valueOf(bill.getPrice()));
        deliveryNoteID.setText(String.valueOf(bill.getDeliveryNoteId()));
    }


    @FXML
    private void saveBill() {
    	boolean paid;
    	if (paidRadioButton.isSelected()) {
    		paid = true;
    	} else paid = false;
        Bill updatedBill = new Bill(bill.getId(), paid,
                Float.parseFloat(price.getText().trim()), Integer.parseInt(deliveryNoteID.getText()));

        try {
            BillDAO bdao = new BillDAO();
            int updated = bdao.updateBill(updatedBill);
            System.out.println("affected rows: "+ updated);
            Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Rechnung geändert!");
    		alert.setHeaderText(null);
    		alert.setContentText("Der bestehende Eintrag wurde erfolgreich aktualisiert!\n");
    		alert.showAndWait();
    		Stage stage = (Stage) cancelButton.getScene().getWindow();
    		stage.close();
        } catch ( Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Achtung!");
    		alert.setHeaderText(null);
    		alert.setContentText("Die Rechnung konnte nicht verändert werden!\n");
    		alert.showAndWait();
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
