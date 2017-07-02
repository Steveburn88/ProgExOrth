package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.BillDAO;
import de.schneefisch.fruas.model.Bill;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;

public class EditBillController {

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField paid;
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
        paid.setText(String.valueOf(bill.getPaid()));
        price.setText(String.valueOf(bill.getPrice()));
        deliveryNoteID.setText(String.valueOf(bill.getDelivery_note_id()));
    }


    @FXML
    private void saveBill() {
        Bill updatedBill = new Bill(bill.getId(), Date.valueOf(paid.getText()),
                Float.parseFloat(price.getText().trim()), Integer.parseInt(deliveryNoteID.getText()));

        try {
            BillDAO bdao = new BillDAO();
            int updated = bdao.updateBill(updatedBill);
            System.out.println("affected rows: "+ updated);
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
