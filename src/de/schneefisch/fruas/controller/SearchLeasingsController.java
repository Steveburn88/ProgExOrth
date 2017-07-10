package de.schneefisch.fruas.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import de.schneefisch.fruas.database.CustomerDAO;
import de.schneefisch.fruas.database.LeasingDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.Leasing;
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
		if(idField.getText().length() > 0) {
			try {
				LeasingDAO lDAO = new LeasingDAO();
				int leasId = Integer.parseInt(idField.getText());
				Leasing leasing = lDAO.selectLeasingById(leasId);
				list.clear();
				list.add(leasing);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		else if(idField.getText().length() == 0 ) {
			try {
				LeasingDAO lDAO = new LeasingDAO();
				List<Leasing> leasingList = lDAO.selectAllLeasings();
				list.clear();
				list.addAll(leasingList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else return;
	}
	@FXML
	private void create(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("createLeasing.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Leasing erstellen");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des Leasing erstellen Fensters!");
			e.printStackTrace();
		}
		CreateLeasingController controller = loader.<CreateLeasingController>getController();		
		stage.show();
	}
	@FXML
	private void edit(ActionEvent event) {
		if(!table.getSelectionModel().isEmpty()) {
			if(table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("nur einen Kunden markieren!");
			} else {
				Leasing getsEdited = table.getSelectionModel().getSelectedItem();				
				showEditLeasing(getsEdited);				
			}
		}
	}
	
	private void showEditLeasing(Leasing leasing) {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("editLeasing.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Leasing bearbeiten");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des Leasing bearbeiten Fensters!");
			e.printStackTrace();
		}
		EditLeasingController controller = loader.<EditLeasingController>getController();
		controller.setLeasing(leasing);
		controller.initData();
		stage.show();
	}
	@FXML
	private void delete(ActionEvent event) {
		if(!table.getSelectionModel().isEmpty()) {
			if(table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("nur einen Kunden markieren!");
			} else {
				Leasing getsRemoved = table.getSelectionModel().getSelectedItem();
				try {
					LeasingDAO lDAO = new LeasingDAO();					
					int removed = lDAO.deleteLeasing(getsRemoved.getId());					
					if(removed == 1 ) {
						list.remove(getsRemoved);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Leasing gelöscht!");
						alert.setHeaderText(null);
						alert.setContentText("Der Leasingeintrag wurde erfolgreich gelöscht!");
						alert.showAndWait();						
					}					
				} catch (Exception e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Achtung!");
					alert.setHeaderText(null);
					alert.setContentText("Der Leasingeintrag konnte nicht gelöscht werden!");
					alert.showAndWait();
				}
				
			}
		}
	}
	@FXML
	private void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
}
