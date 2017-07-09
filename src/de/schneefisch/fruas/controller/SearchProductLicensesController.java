package de.schneefisch.fruas.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import de.schneefisch.fruas.database.CustomerDAO;
import de.schneefisch.fruas.database.LicenseDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.License;
import de.schneefisch.fruas.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SearchProductLicensesController implements Initializable {
	@FXML
	private Button cancelButton;
	@FXML
	private Button editButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button createButton;
	@FXML
	private Button refreshButton;
	@FXML
	private TableView<License> table;
	@FXML
	private TableColumn<License, Integer> id;
	@FXML
	private TableColumn<License, Integer> customerId;
	@FXML
	private TableColumn<License, Integer> productId;
	@FXML
	private TableColumn<License, String> key;
	@FXML
	private TableColumn<License, Boolean> sold;
	@FXML
	private TableColumn<License, Float> discount;
	@FXML
	private TableColumn<License, Date> soldDate;
	@FXML
	private TableColumn<License, Date> endDate;
	@FXML
	private TableColumn<License, Integer> maintenanceId;

	@FXML
	private Product product;
	private ObservableList<License> list = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		id.setCellValueFactory(new PropertyValueFactory<License, Integer>("id"));
		customerId.setCellValueFactory(new PropertyValueFactory<License, Integer>("customerId"));
		productId.setCellValueFactory(new PropertyValueFactory<License, Integer>("productId"));
		key.setCellValueFactory(new PropertyValueFactory<License, String>("key"));
		sold.setCellValueFactory(new PropertyValueFactory<License, Boolean>("sold"));
		discount.setCellValueFactory(new PropertyValueFactory<License, Float>("discount"));
		soldDate.setCellValueFactory(new PropertyValueFactory<License, Date>("soldDate"));
		endDate.setCellValueFactory(new PropertyValueFactory<License, Date>("endDate"));
		maintenanceId.setCellValueFactory(new PropertyValueFactory<License, Integer>("maintenanceId"));
		table.setItems(list);
	}

	public void findLicensesForProduct(Product product) {
		this.product = product;
		try {
			LicenseDAO lDAO = new LicenseDAO();
			List<License> licenseList = lDAO.selectLicensesForProductId(product.getId());
			licenseList.stream().forEach(System.out::println);
			list.addAll(licenseList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void createLicense() {

		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("createLicense.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Lizenz für " + product.getName() + " erstellen.");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des Produktdaten bearbeiten Fensters!");
			e.printStackTrace();
		}
		CreateLicenseController controller = loader.<CreateLicenseController>getController();
		controller.setProductId(product);
		stage.show();
		System.out.println("clearing list");

	}

	@FXML
	private void refresh() {
		try {
			list.clear();
			LicenseDAO lDAO = new LicenseDAO();
			List<License> licenseList = lDAO.selectLicensesForProductId(product.getId());
			licenseList.stream().forEach(System.out::println);
			list.addAll(licenseList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void editLicense() {
		if (!table.getSelectionModel().isEmpty()) {
			if (table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("nur einen Kunden markieren!");
			} else {
				License getsEdited = table.getSelectionModel().getSelectedItem();
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
				controller.setEditableLicense(getsEdited);
				stage.show();
			}
		}
	}

	@FXML
	private void deleteLicense() {
		if(!table.getSelectionModel().isEmpty()) {
			if(table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("nur einen Kunden markieren!");
			} else {
				License getsRemoved = table.getSelectionModel().getSelectedItem();
				try {
					LicenseDAO lDAO = new LicenseDAO();					
					int licId = getsRemoved.getId();
					int removed = lDAO.deleteLicense(licId);					
					if(removed == 1 ) {
						list.remove(getsRemoved);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Lizenz gelöscht!");
						alert.setHeaderText(null);
						alert.setContentText("Gelöschte Daten:\n" + getsRemoved.toStringForAlert());
						alert.showAndWait();						
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
	}

	@FXML
	private void cancel() {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
}
