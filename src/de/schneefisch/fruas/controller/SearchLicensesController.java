package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.LicenseDAO;
import de.schneefisch.fruas.model.License;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class SearchLicensesController implements Initializable{

	@FXML private Button cancelButton;
	@FXML private Button searchButton;
	@FXML private Button editButton;
	@FXML private Button deleteButton;
	@FXML private Button createButton;
	@FXML private Button refreshButton;
	
	@FXML private TextField idField;
	@FXML private TextField customerIdField;
	@FXML private TextField productIdField;
	
	@FXML private TableView<License> table;
	@FXML private TableColumn<License, Integer> id;
	@FXML private TableColumn<License, Integer> customerId;
	@FXML private TableColumn<License, Integer> productId;
	@FXML private TableColumn<License, String> key;
	@FXML private TableColumn<License, Boolean> sold;
	@FXML private TableColumn<License, Float> discount;
	@FXML private TableColumn<License, Date> soldDate;
	@FXML private TableColumn<License, Date> endDate;
	@FXML private TableColumn<License, Integer> maintenanceId;
	private ObservableList<License> list =  FXCollections.observableArrayList();
	private ResourceBundle resources;
	private URL location;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.location = location;
		this.resources = resources;
		try {
			LicenseDAO lDAO = new LicenseDAO();
			List<License> licenseList = lDAO.selectAllLicenses();
			
			
			licenseList.stream().forEach(System.out::println);
			list.addAll(licenseList);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	@FXML
	private void createLicense(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("createLicense.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Neue Lizenz");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des Neuen Lizenz Fensters!");
			e.printStackTrace();
		}
	}
	
	@FXML
	private void cancel (ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	@FXML
	private void searchLicense (ActionEvent event) {
		
	}

	@FXML
	private void refresh() {
		Stage stage = (Stage) refreshButton.getScene().getWindow();
		//table.refresh();
		this.initialize(location, resources);
		stage.show();
	}

	@FXML
	private void deleteLicense (ActionEvent event) {
		
	}
	@FXML
	private void editLicense (ActionEvent event) {
		if(!table.getSelectionModel().isEmpty()) {
			if(table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("Bitte nur eine Lizenz markieren!");
			} else {
				License getsEdited = table.getSelectionModel().getSelectedItem();
				showEditLicense(getsEdited);
			}
		}
	}

	private void showEditLicense(License license) {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("editLicense.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Lizenzdaten bearbeiten");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des Lizenzdatenbearbeiten Fensters!");
			e.printStackTrace();
		}
		EditLicenseController controller = loader.<EditLicenseController>getController();
		controller.initData(license);
		stage.show();
	}
}
