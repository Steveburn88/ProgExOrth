package de.schneefisch.fruas.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import de.schneefisch.fruas.database.CustomerDAO;
import de.schneefisch.fruas.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SearchCustomerController implements Initializable{

	@FXML private Button searchButton;
	@FXML private Button cancelButton;
	@FXML private Button deleteButton;
	@FXML private Button editButton;
	@FXML private TextField idField;
	@FXML private TextField nameField;
	
	@FXML private TableView<Customer> table;
	@FXML private TableColumn<Customer, Integer> id;
	@FXML private TableColumn<Customer, Integer> fiCustomerId;
	@FXML private TableColumn<Customer, Integer> locationId;
	@FXML private TableColumn<Customer, String> salutation;
	@FXML private TableColumn<Customer, String> firstName;
	@FXML private TableColumn<Customer, String> lastName;
	@FXML private TableColumn<Customer, String> phoneNumber;
	@FXML private TableColumn<Customer, String> email;
	@FXML private TableColumn<Customer, String> position;
	@FXML private TableColumn<Customer, String> department;
	@FXML private TableColumn<Customer, String> roomNumber;
	@FXML private TableColumn<Customer, String> buildingNumber;
	@FXML private TableColumn<Customer, String> faxNumber;
	
	private ObservableList<Customer> list =  FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			CustomerDAO cdao = new CustomerDAO();
			List<Customer> customerList = cdao.selectAllCustomers();
			/*DBConnector dbc = new DBConnector();
			List<Customer> customerList = dbc.selectAllCustomers();*/
			customerList.stream().forEach(System.out::println);
			list.addAll(customerList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		id.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
		fiCustomerId.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("fiKuId"));
		locationId.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("locationId"));
		salutation.setCellValueFactory(new PropertyValueFactory<Customer, String>("salutation"));
		firstName.setCellValueFactory(new PropertyValueFactory<Customer, String>("firstName"));
		lastName.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));
		phoneNumber.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
		email.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
		position.setCellValueFactory(new PropertyValueFactory<Customer, String>("position"));
		department.setCellValueFactory(new PropertyValueFactory<Customer, String>("department"));
		roomNumber.setCellValueFactory(new PropertyValueFactory<Customer, String>("roomNumber"));
		buildingNumber.setCellValueFactory(new PropertyValueFactory<Customer, String>("buildingNumber"));
		faxNumber.setCellValueFactory(new PropertyValueFactory<Customer, String>("faxNumber"));
		table.setItems(list);
		
	}
	
	@FXML 
	private void deleteCustomer() {
		if(!table.getSelectionModel().isEmpty()) {
			if(table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("nur einen Kunden markieren!");
			} else {
				Customer getsRemoved = table.getSelectionModel().getSelectedItem();
				try {
					CustomerDAO cdao = new CustomerDAO();					
					int custId = getsRemoved.getId();
					int removed =cdao.deleteCustomer(custId);					
					if(removed == 1 ) {
						list.remove(getsRemoved);
					}					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@FXML
	private void searchCustomer() {
		if(idField.getText().length() > 0) {
			try {
				CustomerDAO cdao = new CustomerDAO();
				int custId = Integer.parseInt(idField.getText());
				Customer customer = cdao.searchCustomerById(custId);
				list.clear();
				list.add(customer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (nameField.getText().length() > 0) {
			try {
				CustomerDAO cdao = new CustomerDAO();
				List<Customer> customerList = cdao.searchCustomersByName(nameField.getText());
				list.clear();
				list.addAll(customerList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(idField.getText().length() == 0 && nameField.getText().length() == 0) {
			try {
				CustomerDAO cdao = new CustomerDAO();
				List<Customer> customerList = cdao.selectAllCustomers();
				list.clear();
				list.addAll(customerList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else return;
		
	}
	
	@FXML
	private void editCustomer() {
		if(!table.getSelectionModel().isEmpty()) {
			if(table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("nur einen Kunden markieren!");
			} else {
				Customer getsEdited = table.getSelectionModel().getSelectedItem();				
				showEditCustomer(getsEdited);				
			}
		}
	}
	
	private void showEditCustomer(Customer customer) {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("editCustomer.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Kundendaten bearbeiten");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim öffnen des kundendaten bearbeiten Fensters!");
			e.printStackTrace();
		}
		EditCustomerController controller = loader.<EditCustomerController>getController();
		controller.initData(customer);
		stage.show();
	}
	@FXML
	private void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

	
}
