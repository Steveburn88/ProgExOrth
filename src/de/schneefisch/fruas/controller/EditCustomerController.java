package de.schneefisch.fruas.controller;


import de.schneefisch.fruas.database.CustomerDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.Salutation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EditCustomerController  {

	@FXML
	private Button saveButton;
	@FXML
	private ToggleGroup salutation;
	@FXML
	private RadioButton herr;
	@FXML
	private RadioButton frau;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private TextField phoneNumber;
	@FXML
	private TextField position;
	@FXML
	private TextField department;
	@FXML
	private TextField email;
	@FXML
	private TextField roomNumber;
	@FXML
	private TextField buildingNumber;
	@FXML
	private TextField faxNumber;
	@FXML
	private Button cancelButton;	
	@FXML
	private Customer customer;

	void initialize() {
	};

	void initData(Customer customer) {
		this.customer = customer;
		System.out.println(customer.getId());
		if(customer.getSalutation().toString().equals("Herr")) {
			herr.setSelected(true);
		} else frau.setSelected(true);
		firstName.setText(customer.getFirstName());
		lastName.setText(customer.getLastName());
		phoneNumber.setText(customer.getPhoneNumber());
		position.setText(customer.getPosition());
		department.setText(customer.getDepartment());
		email.setText(customer.getEmail());
		roomNumber.setText(customer.getRoomNumber());
		buildingNumber.setText(customer.getBuildingNumber());
		faxNumber.setText(customer.getFaxNumber());	
		
	}


	@FXML
	private void saveCustomer() {
		Salutation salut = Salutation.Herr;
		if (frau.isSelected()) {
			salut = Salutation.Frau;
		}
		Customer updatedCustomer = new Customer(customer.getId(), customer.getFiKuId(), customer.getLocationId(), salut, firstName.getText(), lastName.getText(),
				phoneNumber.getText(), email.getText(), position.getText(), department.getText(), 
				roomNumber.getText(), buildingNumber.getText(), faxNumber.getText());
		int updated= 0;
		try {
			CustomerDAO cdao = new CustomerDAO();
			updated = cdao.updateCustomer(updatedCustomer);
			System.out.println("affected rows: "+ updated);
		} catch ( Exception e) {
			e.printStackTrace();
		}
		if(updated == 1) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Kundendaten aktualisiert!");
			alert.setHeaderText(null);
			alert.setContentText("Neuer Informationen:\n" + updatedCustomer.toStringForAlert());
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
