package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.LicenseDAO;
import de.schneefisch.fruas.model.License;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditLicenseController {

    @FXML
    private Button saveButton;
    @FXML
    private TextField customerID;
    @FXML
    private TextField productID;
    @FXML
    private TextField key;
    @FXML
    private TextField sold;
    @FXML
    private TextField discount;
    @FXML
    private TextField soldDate;
    @FXML
    private TextField endDate;
    @FXML
    private TextField maintenanceID;
    @FXML
    private Button cancelButton;
    @FXML
    private License license;


    void initialize() {

    }

    void initData(License license) {
        this.license = license;
        System.out.println(license.getId());
        customerID.setText(String.valueOf(license.getCustomerId()));
        productID.setText(String.valueOf(license.getProductId()));
        key.setText(license.getKey());
        sold.setText(String.valueOf(license.isSold()));
        discount.setText(String.valueOf(license.getDiscount()));
        soldDate.setText(String.valueOf(license.getSoldDate()));
        endDate.setText(String.valueOf(license.getEndDate()));
        maintenanceID.setText(String.valueOf(license.getMaintenanceId()));
    }

    @FXML
    private void saveLicense() {
        License updatedlicense = new License(license.getId(), license.getCustomerId(), license.getProductId(), license.getKey(),
                license.isSold(), license.getDiscount(), license.getSoldDate(), license.getEndDate(), license.getMaintenanceId());

        int updated= 0;
        try {
            LicenseDAO ldao = new LicenseDAO();
            updated = ldao.updateLicense(updatedlicense);
            System.out.println("affected rows: "+ updated);
        } catch ( Exception e) {
            e.printStackTrace();
        }
        if(updated == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Lizenzdaten aktualisiert!");
            alert.setHeaderText(null);
            alert.setContentText("Neue Informationen:\n" + updatedlicense.toStringForAlert());
            alert.showAndWait();
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
