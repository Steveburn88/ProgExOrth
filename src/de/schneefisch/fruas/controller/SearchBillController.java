package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.BillDAO;
import de.schneefisch.fruas.model.Bill;
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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SearchBillController implements Initializable {
	@FXML private Button searchButton;
	@FXML private Button editButton;
	@FXML private Button deleteButton;
	@FXML private Button showButton;
	@FXML private Button cancelButton;
	@FXML private TextField idField;

	@FXML private TableView<Bill> table;
	@FXML private TableColumn<Bill, Integer> id;
	@FXML private TableColumn<Bill, String> paid;
	@FXML private TableColumn<Bill, Float> price;
	@FXML private TableColumn<Bill, String> deliveryNoteID;

	private ObservableList<Bill> list =  FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			BillDAO cdao = new BillDAO();
			List<Bill> billList = cdao.selectAllBills();
			/*DBConnector dbc = new DBConnector();
			List<Customer> customerList = dbc.selectAllCustomers();*/
			billList.stream().forEach(System.out::println);
			list.addAll(billList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		id.setCellValueFactory(new PropertyValueFactory<Bill, Integer>("id"));
		paid.setCellValueFactory(new PropertyValueFactory<Bill, String>("paid"));
		price.setCellValueFactory(new PropertyValueFactory<Bill, Float>("price"));
		deliveryNoteID.setCellValueFactory(new PropertyValueFactory<Bill, String>("deliveryNoteID"));
		table.setItems(list);
	}

	@FXML
	private void searchBill() {
		if(idField.getText().length() > 0) {
			try {
				BillDAO bdao = new BillDAO();
				int billId = Integer.parseInt(idField.getText());
				Bill bill = bdao.searchBillById(billId);
				list.clear();
				list.add(bill);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(idField.getText().length() == 0) {
			try {
				BillDAO bdao = new BillDAO();
				List<Bill> billList = bdao.selectAllBills();
				list.clear();
				list.addAll(billList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else return;
	}

	@FXML
	private void editBill() {
		if(!table.getSelectionModel().isEmpty()) {
			if(table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("Bitte nur eine Rechnung markieren!");
			} else {
				Bill getsEdited = table.getSelectionModel().getSelectedItem();
				showEditBill(getsEdited);
			}
		}
	}

	private void showEditBill(Bill bill) {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("editBill.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Rechnungsdaten bearbeiten");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim Öffnen des Rechnungsdaten bearbeiten Fensters!");
			e.printStackTrace();
		}
		EditBillController controller = loader.<EditBillController>getController();
		controller.initData(bill);
		stage.show();
	}

	@FXML
	private void deleteBill() {
		if(!table.getSelectionModel().isEmpty()) {
			if(table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("Bitte nur eine Rechnung markieren!");
			} else {
				Bill getsRemoved = table.getSelectionModel().getSelectedItem();
				try {
					BillDAO bdao = new BillDAO();
					int billId = getsRemoved.getId();
					int removed = bdao.deleteBill(billId);
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
	private void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	

}
