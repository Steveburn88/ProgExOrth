package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.MaintenanceDAO;
import de.schneefisch.fruas.model.Maintenance;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;

public class EditMaintenanceController {

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField info;
    @FXML
    private TextField price;
    @FXML
    private TextField start;
    @FXML
    private TextField end;
    @FXML
    private Maintenance maintenance;

    void initialize() {
    };

    void initData(Maintenance maintenance) {
        this.maintenance = maintenance;
        System.out.println(maintenance.getId());
        info.setText(String.valueOf(maintenance.getInfo()));
        price.setText(String.valueOf(maintenance.getPrice()));
        start.setText(String.valueOf(maintenance.getStart()));
        end.setText(String.valueOf(maintenance.getEnd()));
    }


    @FXML
    private void saveMaintenance() {
        Maintenance updatedMaintenance = new Maintenance(maintenance.getId(), info.getText(),
                Float.parseFloat(price.getText().trim()), Date.valueOf(start.getText()), Date.valueOf(end.getText()));

        try {
            MaintenanceDAO mdao = new MaintenanceDAO();
            int updated = mdao.updateMaintenance(updatedMaintenance);
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
