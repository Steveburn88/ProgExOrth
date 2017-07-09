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
import de.schneefisch.fruas.model.Offer;
import de.schneefisch.fruas.transactions.DeliveryNotePDFCreator;
import de.schneefisch.fruas.transactions.OfferPDFCreator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SearchDeliveryNoteController implements Initializable {

	@FXML
	private TextField idField;
	@FXML
	private Button cancelButton;
	@FXML
	private Button createButton;
	@FXML
	private Button searchPositionsButton;
	@FXML
	private Button pdfButton;
	@FXML
	private TableView<DeliveryNote> table;
	@FXML
	private TableColumn<DeliveryNote, Integer> id;
	@FXML
	private TableColumn<DeliveryNote, Date> date;
	private ObservableList<DeliveryNote> list = FXCollections.observableArrayList();

	@FXML
	private void searchDeliveryNote() {
		if (idField.getText().length() > 0) {
			try {
				DeliveryNoteDAO dnDAO = new DeliveryNoteDAO();
				int dnId = Integer.parseInt(idField.getText());
				DeliveryNote dn = dnDAO.selectDeliveryNoteById(dnId);
				list.clear();
				list.add(dn);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (idField.getText().length() == 0) {
			try {
				DeliveryNoteDAO dnDAO = new DeliveryNoteDAO();
				List<DeliveryNote> dnList = dnDAO.selectAllDeliveryNotes();
				list.clear();
				list.addAll(dnList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			return;
	}
	
	@FXML
	private void createPDF() {
		DeliveryNote deliveryNote = null;
		if (!table.getSelectionModel().isEmpty()) {
			if (table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("nur einen Kunden markieren!");
			} else {
				deliveryNote = table.getSelectionModel().getSelectedItem();
			}
		}
		System.out.println("erstelle pdf fuer lieferschein " + deliveryNote.getId() + ".");
		DeliveryNotePDFCreator oc = new DeliveryNotePDFCreator(deliveryNote);
		oc.createPDF();
	}

	@FXML
	private void editDeliveryNote(ActionEvent event) {
		DeliveryNote dn = null;
		if (!table.getSelectionModel().isEmpty()) {
			if (table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("nur einen Kunden markieren!");
			} else {
				dn = table.getSelectionModel().getSelectedItem();
			}
		}
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("editDeliveryNote.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Lieferschein bearbeiten");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des Lieferschein bearbeiten Fensters!");
			e.printStackTrace();
		}
		EditDeliveryNoteController controller = loader.<EditDeliveryNoteController>getController();
		controller.setDeliveryNote(dn);
		controller.initData();
		stage.show();
	}

	@FXML
	private void deleteDeliveryNote(ActionEvent event) {
		if (!table.getSelectionModel().isEmpty()) {
			if (table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("nur einen Kunden markieren!");
			} else {
				DeliveryNote getsRemoved = table.getSelectionModel().getSelectedItem();
				try {
					DeliveryNoteDAO dnDAO = new DeliveryNoteDAO();
					int removed = dnDAO.deleteDeliveryNote(getsRemoved.getId());
					if (removed == 1) {
						list.remove(getsRemoved);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Lieferschein gelöscht!");
						alert.setHeaderText(null);
						alert.setContentText("Der Lieferschein wurde erfolgreich gelöscht!");
						alert.showAndWait();
					}
				} catch (Exception e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Achtung!");
					alert.setHeaderText(null);
					alert.setContentText("Der Lieferschein konnte nicht gelöscht werden! Es bestehen zugehörige Lieferscheinpositionen!");
					alert.showAndWait();
				}

			}
		}
	}

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
		if (!table.getSelectionModel().isEmpty()) {
			if (table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("nur einen Kunden markieren!");
			} else {
				dn = table.getSelectionModel().getSelectedItem();
			}
		}
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("searchDeliveryNotePositions.fxml"));
		Stage stage = new Stage();
		stage.setTitle("LieferscheinPositionen für Lieferschein-ID: " + dn.getId());
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des LieferscheinPositionen-Fensters!");
			e.printStackTrace();
		}
		SearchDeliveryNotePositionController controller = loader.<SearchDeliveryNotePositionController>getController();
		controller.setDeliveryNote(dn);
		controller.initializeData();
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
