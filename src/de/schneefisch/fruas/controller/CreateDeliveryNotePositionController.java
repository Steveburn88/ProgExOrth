package de.schneefisch.fruas.controller;

import java.io.IOException;
import java.sql.SQLException;

import de.schneefisch.fruas.database.DeliveryNotePositionDAO;
import de.schneefisch.fruas.database.LicenseDAO;
import de.schneefisch.fruas.model.DeliveryNote;
import de.schneefisch.fruas.model.DeliveryNotePosition;
import de.schneefisch.fruas.model.License;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CreateDeliveryNotePositionController {

	@FXML
	private TextField licenseIdField;
	@FXML
	private Button createDeliveryNoteButton;
	@FXML
	private Button cancelButton;
	@FXML
	private DeliveryNote deliveryNote;

	@FXML
	private void createDeliveryNotePosition(ActionEvent event) {
		DeliveryNotePosition dnp = new DeliveryNotePosition(deliveryNote.getId(),
				Integer.valueOf(licenseIdField.getText()));

		DeliveryNotePositionDAO dnpDAO = new DeliveryNotePositionDAO();
		LicenseDAO lDAO = new LicenseDAO();

		try {
			License license = lDAO.selectLicenseById(Integer.valueOf(licenseIdField.getText()));
			if (license != null) {
				if (!license.isSold()) {
					FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("editLicense.fxml"));
					Stage stage = new Stage();
					stage.setTitle("Lizenz bearbeiten");
					try {
						stage.setScene(new Scene(loader.load()));
					} catch (IOException e) {
						System.out.println("Fehler beim Oeffnen des Lizenz bearbeiten Fensters!");
						e.printStackTrace();
					}
					EditLicenseController controller = loader.<EditLicenseController>getController();
					controller.setEditableLicense(license);
					stage.show();
					dnpDAO.insertDeliveryNotePosition(dnp);
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Erstellen nicht möglich!");
					alert.setHeaderText(null);
					alert.setContentText("Diese Lizenz ist schon verkauft.\n"); // +
																				// insertedLicense.toStringForAlert());
					alert.showAndWait();

					Stage stage = (Stage) cancelButton.getScene().getWindow();
					stage.close();
				}

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Erstellen nicht möglich!");
				alert.setHeaderText(null);
				alert.setContentText("Diese Lizenz existiert nicht.\n"); // +
																			// insertedLicense.toStringForAlert());
				alert.showAndWait();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	public void initData(DeliveryNote deliveryNote) {
		this.deliveryNote = deliveryNote;

	}
}
