package de.schneefisch.fruas.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import de.schneefisch.fruas.database.CustomerDAO;
import de.schneefisch.fruas.database.LocationDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.FiCustomer;
import de.schneefisch.fruas.model.Location;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SearchLocationController implements Initializable {

	@FXML
	private Button createButton;
	@FXML
	private Button editButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button refreshButton;
	@FXML
	private Button cancelButton;

	@FXML
	private TableView<Location> table;
	@FXML
	private TableColumn<Location, Integer> id;
	@FXML
	private TableColumn<Location, Integer> fiCustomerId;
	@FXML
	private TableColumn<Location, String> postalCode;
	@FXML
	private TableColumn<Location, String> city;
	@FXML
	private TableColumn<Location, String> street;
	@FXML
	private TableColumn<Location, String> houseNumber;
	@FXML
	private TableColumn<Location, String> postBox;

	@FXML
	private FiCustomer fiCustomer;
	private ObservableList<Location> list = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void setFiCustomer(FiCustomer fiCustomer) {
		this.fiCustomer = fiCustomer;
	}

	public void initTable() {
		LocationDAO lDAO = new LocationDAO();
		try {
			List<Location> locations = lDAO.findLocationsByFiCustomerId(fiCustomer.getId());
			list.addAll(locations);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		id.setCellValueFactory(new PropertyValueFactory<Location, Integer>("id"));
		fiCustomerId.setCellValueFactory(new PropertyValueFactory<Location, Integer>("fiKuId"));
		postalCode.setCellValueFactory(new PropertyValueFactory<Location, String>("postalCode"));
		city.setCellValueFactory(new PropertyValueFactory<Location, String>("city"));
		street.setCellValueFactory(new PropertyValueFactory<Location, String>("street"));
		houseNumber.setCellValueFactory(new PropertyValueFactory<Location, String>("houseNumber"));
		postBox.setCellValueFactory(new PropertyValueFactory<Location, String>("postBox"));

		table.setItems(list);
	}
	@FXML
	public void create(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("createLocation.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Standort hinzufügen");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des Standort hinzufügen Fensters!");
			e.printStackTrace();
		}
		CreateLocationController controller = loader.<CreateLocationController>getController();
		controller.setFiCustomer(fiCustomer);
		stage.show();
	}
	@FXML
	public void delete(ActionEvent event) {
		if(!table.getSelectionModel().isEmpty()) {
			if(table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("nur einen Kunden markieren!");
			} else {
				Location getsRemoved = table.getSelectionModel().getSelectedItem();
				try {
					LocationDAO lDAO = new LocationDAO();
					int custId = getsRemoved.getId();
					int removed = lDAO.deleteLocation(custId);
					
										
					if(removed == 1 ) {
						list.remove(getsRemoved);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Standort gelöscht!");
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
	public void edit(ActionEvent event) {

		if(!table.getSelectionModel().isEmpty()) {
			if(table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("nur einen Kunden markieren!");
			} else {
				Location getsEdited = table.getSelectionModel().getSelectedItem();				
				FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("editLocation.fxml"));
				Stage stage = new Stage();
				stage.setTitle("Standort bearbeiten");
				try {
					stage.setScene(new Scene(loader.load()));
				} catch (IOException e) {
					System.out.println("Fehler beim Oeffnen des Standort bearbeiten Fensters!");
					e.printStackTrace();
				}
				EditLocationController controller = loader.<EditLocationController>getController();
				controller.setFiCustomer(fiCustomer);
				controller.setLocation(getsEdited);
				controller.initData();
				stage.show();			
			}
		}
	}
	
	
	@FXML
	public void refresh(ActionEvent event) {
		LocationDAO lDAO = new LocationDAO();
		try {
			List<Location> locations = lDAO.findLocationsByFiCustomerId(fiCustomer.getId());
			list.clear();
			list.addAll(locations);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		table.setItems(list);
	}
	@FXML
	public void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

}
