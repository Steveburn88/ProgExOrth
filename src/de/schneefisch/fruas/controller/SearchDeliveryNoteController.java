package de.schneefisch.fruas.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import de.schneefisch.fruas.database.CustomerDAO;
import de.schneefisch.fruas.database.DeliveryNoteDAO;
import de.schneefisch.fruas.database.LicenseDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.DeliveryNote;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SearchDeliveryNoteController implements Initializable {
	@FXML
	private Button cancelButton;
	@FXML
	private Button createButton;
	@FXML
	private Button searchPositionsButton;
	@FXML
	private TableView<DeliveryNote> table;
	@FXML
	private TableColumn<DeliveryNote, Integer> id;
	@FXML
	private TableColumn<DeliveryNote, Date> date;
	private ObservableList<DeliveryNote> list = FXCollections.observableArrayList();

	@FXML
	private void createDeliveryNote(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("createDeliveryNote.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Neuer Lieferschein");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			System.out.println("Fehler beim öffnen des Neuen Lieferschein Fensters!");
			e.printStackTrace();
		}
	}

	@FXML
	private void searchDeliveryNotePositions(ActionEvent event) {
		DeliveryNote dn = null;
		if(!table.getSelectionModel().isEmpty()) {
			if(table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("nur einen Kunden markieren!");
			} else {
				dn = table.getSelectionModel().getSelectedItem();				
			}
		}
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("searchDeliveryNotePositions.fxml"));
		Stage stage = new Stage();
		stage.setTitle("LieferscheinPositionen");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim Ã¶ffnen des Neuer LieferscheinPositionen Fensters!");
			e.printStackTrace();
		}
		SearchDeliveryNotePositionController controller = loader.<SearchDeliveryNotePositionController>getController();
		controller.initData(dn);
		System.out.println(dn);
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

			DeliveryNoteDAO dnDAO = new DeliveryNoteDAO();
			List<DeliveryNote> dnList = dnDAO.selectAllDeliveryNotes();

			dnList.stream().forEach(System.out::println);
			list.addAll(dnList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		id.setCellValueFactory(new PropertyValueFactory<DeliveryNote, Integer>("id"));
		date.setCellValueFactory(new PropertyValueFactory<DeliveryNote, Date>("date"));

		table.setItems(list);

	}
}
