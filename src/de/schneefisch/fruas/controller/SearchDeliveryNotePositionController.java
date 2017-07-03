package de.schneefisch.fruas.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import de.schneefisch.fruas.database.DeliveryNotePositionDAO;
import de.schneefisch.fruas.model.DeliveryNote;
import de.schneefisch.fruas.model.DeliveryNotePosition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SearchDeliveryNotePositionController {

	@FXML
	private Button createButton;
	@FXML
	private Button cancelButton;
	@FXML
	private Button editButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button initializeButton;
	

	@FXML
	private TableView<DeliveryNotePosition> table;
	@FXML
	private TableColumn<DeliveryNotePosition, Integer> id;
	@FXML
	private TableColumn<DeliveryNotePosition, Integer> deliveryNoteId;
	@FXML
	private TableColumn<DeliveryNotePosition, Integer> licenseId;
	private ObservableList<DeliveryNotePosition> list = FXCollections.observableArrayList();

	@FXML
	private DeliveryNote deliveryNote;

	void initData(DeliveryNote deliveryNote) {
		System.out.println("in initData:"+deliveryNote);
		this.deliveryNote = deliveryNote;
	}
	
	@FXML
	private void initializeData() {
		list.clear();
		try {

			DeliveryNotePositionDAO dnpDAO = new DeliveryNotePositionDAO();
			System.out.println(this.deliveryNote.getId());
			List<DeliveryNotePosition> dnpList = dnpDAO.selectAllPositionsForDeliveryNote(deliveryNote.getId());

			dnpList.stream().forEach(System.out::println);
			list.addAll(dnpList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		id.setCellValueFactory(new PropertyValueFactory<DeliveryNotePosition, Integer>("id"));
		deliveryNoteId.setCellValueFactory(new PropertyValueFactory<DeliveryNotePosition, Integer>("deliveryNoteId"));
		licenseId.setCellValueFactory(new PropertyValueFactory<DeliveryNotePosition, Integer>("licenseId"));
		table.setItems(list);

	}

	@FXML
	private void createDeliveryNotePosition(ActionEvent event) {
		
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("createDeliveryNotePosition.fxml"));
		Stage stage = new Stage();
		stage.setTitle("LieferscheinPosition erstellen");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim öffnen des Neuer LieferscheinPositionen Fensters!");
			e.printStackTrace();
		}
		CreateDeliveryNotePositionController controller = loader.<CreateDeliveryNotePositionController>getController();
		controller.initData(this.deliveryNote);
		stage.show();
	}

	@FXML
	private void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
}
