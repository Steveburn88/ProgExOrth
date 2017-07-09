package de.schneefisch.fruas.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import de.schneefisch.fruas.database.LeasingDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.Leasing;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SearchLeasingsController implements Initializable{

	@FXML private Button searchButton;
	@FXML private Button createButton;
	@FXML private Button editButton;
	@FXML private Button deleteButton;
	@FXML private Button cancelButton;
	
	@FXML private TextField idField;
	
	@FXML private TableView<Leasing> table;
	@FXML private TableColumn<Leasing, Integer> id;
	@FXML private TableColumn<Leasing, Integer> customerId;
	@FXML private TableColumn<Leasing, Integer> productId;
	@FXML private TableColumn<Leasing, Date> startDate;
	@FXML private TableColumn<Leasing, Date> firstBillDate;
	@FXML private TableColumn<Leasing, Float> billPayment;
	@FXML private TableColumn<Leasing, Date> dueBillDate;
	@FXML private TableColumn<Leasing, Integer> numberOfBills;
	@FXML private TableColumn<Leasing, Date> recentBillDate;	
	
	private ObservableList<Leasing> list =  FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try {
			LeasingDAO leasingDAO = new LeasingDAO();
			List<Leasing> leasingList = leasingDAO.selectAllLeasings();
			list.addAll(leasingList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		id.setCellValueFactory(new PropertyValueFactory<Leasing, Integer>("id"));
		customerId.setCellValueFactory(new PropertyValueFactory<Leasing, Integer>("customerId"));
		productId.setCellValueFactory(new PropertyValueFactory<Leasing, Integer>("productId"));
		startDate.setCellValueFactory(new PropertyValueFactory<Leasing, Date>("startDate"));
		firstBillDate.setCellValueFactory(new PropertyValueFactory<Leasing, Date>("firstBillDate"));
		billPayment.setCellValueFactory(new PropertyValueFactory<Leasing, Float>("billPayment"));
		dueBillDate.setCellValueFactory(new PropertyValueFactory<Leasing, Date>("dueBillDate"));
		numberOfBills.setCellValueFactory(new PropertyValueFactory<Leasing, Integer>("numberOfBills"));
		recentBillDate.setCellValueFactory(new PropertyValueFactory<Leasing, Date>("recentBillDate"));
		table.setItems(list);
		
	}
	
	@FXML
	private void search(ActionEvent event) {
		
	}
	@FXML
	private void create(ActionEvent event) {
		
	}
	@FXML
	private void edit(ActionEvent event) {
		
	}
	@FXML
	private void delete(ActionEvent event) {
		
	}
	@FXML
	private void cancel(ActionEvent event) {
		
	}
}
