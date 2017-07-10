package de.schneefisch.fruas.controller;



import de.schneefisch.fruas.database.CustomerDAO;
import de.schneefisch.fruas.database.MaintenanceDAO;
import de.schneefisch.fruas.database.OfferDAO;
import de.schneefisch.fruas.database.ProductDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.Maintenance;
import de.schneefisch.fruas.model.Offer;
import de.schneefisch.fruas.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CreateOfferController implements Initializable{

	@FXML private TextField customerId;
	@FXML private DatePicker validityDatePicker;
	@FXML private ComboBox<String> customerBox;    
    @FXML private Button createOfferButton;
    @FXML private Button cancelButton;
    
    private ObservableList<String> customerList = FXCollections.observableArrayList();
    
    @FXML
    private void createOffer(ActionEvent event) {
    	 Offer offer = new Offer(Integer.valueOf(customerId.getText()) , Date.valueOf(validityDatePicker.getValue()));
         try {
             OfferDAO odao = new OfferDAO();
             odao.insertOffer(offer);
         } catch (Exception e) {
             e.printStackTrace();
         }
         Stage stage = (Stage) createOfferButton.getScene().getWindow();
         stage.close();
    }


    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		CustomerDAO cDAO = new CustomerDAO();
		
		try {
			List<Customer> cList = cDAO.selectAllCustomers();
			List<String> customerStringList = cList.stream().map(c -> c.toStringForList()).collect(Collectors.toList());
			customerList.addAll(customerStringList);
			customerBox.getItems().addAll(customerList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		customerBox.setOnAction(e -> customerId.setText(customerBox.getValue().substring(customerBox.getValue().indexOf("[")+1,customerBox.getValue().indexOf("]") )));
		
	}
}

