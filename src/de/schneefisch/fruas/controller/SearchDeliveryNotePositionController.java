package de.schneefisch.fruas.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import de.schneefisch.fruas.database.DeliveryNoteDAO;
import de.schneefisch.fruas.database.DeliveryNotePositionDAO;
import de.schneefisch.fruas.model.DeliveryNote;
import de.schneefisch.fruas.model.DeliveryNotePosition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
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

	void setDeliveryNote(DeliveryNote deliveryNote) {

		this.deliveryNote = deliveryNote;
	}

	@FXML
	void initializeData() {
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
	private void deleteDeliveryNotePosition(ActionEvent event) {
		if (!table.getSelectionModel().isEmpty()) {
			if (table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("nur einen Kunden markieren!");
			} else {
				DeliveryNotePosition getsRemoved = table.getSelectionModel().getSelectedItem();
				try {
					DeliveryNotePositionDAO dnpDAO = new DeliveryNotePositionDAO();
					int removed = dnpDAO.deleteDeliveryNotePosition(getsRemoved.getId());
					if (removed == 1) {
						list.remove(getsRemoved);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Lieferscheinposition gelöscht!");
						alert.setHeaderText(null);
						alert.setContentText("Die Lieferscheinposition wurde erfolgreich gelöscht!");
						alert.showAndWait();
					}
				} catch (Exception e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Achtung!");
					alert.setHeaderText(null);
					alert.setContentText("Die Lieferscheinposition konnte nicht gelöscht werden");
					alert.showAndWait();
				}

			}
		}
	}

	@FXML
	private void createDeliveryNotePosition(ActionEvent event) {

		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("createDeliveryNotePosition.fxml"));
		Stage stage = new Stage();
		stage.setTitle("LieferscheinPosition erstellen");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim Ã¶ffnen des Neuer LieferscheinPositionen Fensters!");
			e.printStackTrace();
		}
		CreateDeliveryNotePositionController controller = loader.<CreateDeliveryNotePositionController>getController();
		controller.setDeliveryNote(this.deliveryNote);
		stage.show();
	}

	@FXML
	private void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

}
