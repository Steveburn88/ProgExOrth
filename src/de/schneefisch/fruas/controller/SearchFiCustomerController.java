package de.schneefisch.fruas.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import de.schneefisch.fruas.database.CustomerDAO;
import de.schneefisch.fruas.database.FiCustomerDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.FiCustomer;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SearchFiCustomerController implements Initializable {

	@FXML private Button searchButton;
	@FXML private Button editButton;
	@FXML private Button deleteButton;
	@FXML private Button searchLocationButton;
	@FXML private Button cancelButton;
	
	@FXML private TextField idField;
	@FXML private TextField nameField;
	
	@FXML private TableView<FiCustomer> table;
	@FXML private TableColumn<FiCustomer, Integer> id;
	@FXML private TableColumn<FiCustomer, String> name;
	
	
	private ObservableList<FiCustomer> list =  FXCollections.observableArrayList();
	
	@FXML
	private void search(ActionEvent event) {
		if(idField.getText().length() > 0) {
			try {
				FiCustomerDAO cdao = new FiCustomerDAO();
				int custId = Integer.parseInt(idField.getText());
				FiCustomer customer = cdao.selectFiCustomer(custId);
				list.clear();
				list.add(customer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (nameField.getText().length() > 0) {
			try {
				FiCustomerDAO cdao = new FiCustomerDAO();
				List<FiCustomer> customerList = cdao.searchFiCustomersByName(nameField.getText());
				list.clear();
				list.addAll(customerList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(idField.getText().length() == 0 && nameField.getText().length() == 0) {
			try {
				FiCustomerDAO cdao = new FiCustomerDAO();
				List<FiCustomer> customerList = cdao.selectAllFiCustomers();
				list.clear();
				list.addAll(customerList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else return;
		
	}
	
	@FXML
	private void edit(ActionEvent event) {
		if(!table.getSelectionModel().isEmpty()) {
			if(table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("nur einen Kunden markieren!");
			} else {
				FiCustomer getsEdited = table.getSelectionModel().getSelectedItem();				
				showEditCustomer(getsEdited);				
			}
		}
	}
	
	private void showEditCustomer(FiCustomer customer) {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("editFiCustomer.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Kundendaten bearbeiten");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des kundendaten bearbeiten Fensters!");
			e.printStackTrace();
		}
		EditFiCustomerController controller = loader.<EditFiCustomerController>getController();
		controller.setFiCustomer(customer);
		controller.initData();
		stage.show();
	}
	
	@FXML
	private void delete(ActionEvent event) {
		if(!table.getSelectionModel().isEmpty()) {
			if(table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("nur einen Kunden markieren!");
			} else {
				FiCustomer getsRemoved = table.getSelectionModel().getSelectedItem();
				try {
					FiCustomerDAO cdao = new FiCustomerDAO();				
					int custId = getsRemoved.getId();
					int removed =cdao.deleteFiCustomer(custId);		
					if(removed == 1 ) {
						list.remove(getsRemoved);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Kunde gelöscht!");
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
	private void searchLocation(ActionEvent event) {
		if(!table.getSelectionModel().isEmpty()) {
			if(table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("nur einen Kunden markieren!");
			} else {
				FiCustomer getsEdited = table.getSelectionModel().getSelectedItem();				
				showSearchLocation(getsEdited);				
			}
		}
	}
	
	private void showSearchLocation(FiCustomer customer) {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("searchLocation.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Standorte");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des Standorte Fensters!");
			e.printStackTrace();
		}
		SearchLocationController controller = loader.<SearchLocationController>getController();
		controller.setFiCustomer(customer);
		controller.initTable();
		stage.show();
	}
	
	@FXML
	private void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			FiCustomerDAO cdao = new FiCustomerDAO();
			List<FiCustomer> fiCustomerList = cdao.selectAllFiCustomers();
		
			fiCustomerList.stream().forEach(System.out::println);
			list.addAll(fiCustomerList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		id.setCellValueFactory(new PropertyValueFactory<FiCustomer, Integer>("id"));
		name.setCellValueFactory(new PropertyValueFactory<FiCustomer, String>("name"));
		table.setItems(list);
		
	}
}
