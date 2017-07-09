package de.schneefisch.fruas.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import de.schneefisch.fruas.database.CustomerDAO;
import de.schneefisch.fruas.database.OfferDAO;
import de.schneefisch.fruas.database.ProductDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.Offer;
import de.schneefisch.fruas.model.Product;
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

public class SearchOfferController implements Initializable {

	@FXML
	private Button cancelButton;
	@FXML
	private Button addPositionButton;
	@FXML
	private Button createButton;
	@FXML
	private TextField idField;
	@FXML
	private TextField customerIdField;
	@FXML
	private Button createPDFButton;

	private ObservableList<Offer> list = FXCollections.observableArrayList();

	@FXML
	private TableView<Offer> table;
	@FXML
	private TableColumn<Offer, Integer> id;
	@FXML
	private TableColumn<Offer, Integer> customerId;
	@FXML
	private TableColumn<Offer, Date> validity;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			OfferDAO odao = new OfferDAO();
			List<Offer> offerList = odao.selectAllOffers();

			offerList.stream().forEach(System.out::println);
			list.addAll(offerList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		id.setCellValueFactory(new PropertyValueFactory<Offer, Integer>("id"));
		customerId.setCellValueFactory(new PropertyValueFactory<Offer, Integer>("customerId"));
		validity.setCellValueFactory(new PropertyValueFactory<Offer, Date>("validity"));
		table.setItems(list);
	}

	@FXML
	private void showCreateOfferPosition() {
		Offer offer = new Offer();
		if (!table.getSelectionModel().isEmpty()) {
			if (table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("nur einen Kunden markieren!");
			} else {
				offer = table.getSelectionModel().getSelectedItem();
			}
		}
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("createOfferPosition.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Neue AngebotsPosition");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des angebotsPositions Fensters!");
			e.printStackTrace();
		}
		CreateOfferPositionController controller = loader.<CreateOfferPositionController>getController();
		controller.initData(offer);
		stage.show();
	}

	@FXML
	private void editOffer() {
		if (!table.getSelectionModel().isEmpty()) {
			if (table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("Bitte nur ein Angebot markieren!");
			} else {
				Offer getsEdited = table.getSelectionModel().getSelectedItem();
				showEditOffer(getsEdited);
			}
		}
	}

	private void showEditOffer(Offer getsEdited) {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("editOffer.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Angebotsdaten bearbeiten");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des Angebotsdaten bearbeiten Fensters!");
			e.printStackTrace();
		}
		EditOfferController controller = loader.<EditOfferController>getController();
		controller.setOffer(getsEdited);
		controller.initTextFields();
		stage.show();

	}

	@FXML
	private void editOfferPositions() {
		if (!table.getSelectionModel().isEmpty()) {
			if (table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("Bitte nur ein Angebot markieren!");
			} else {
				Offer getsEdited = table.getSelectionModel().getSelectedItem();
				showEditOfferPosition(getsEdited);
			}
		}
	}

	private void showEditOfferPosition(Offer offer) {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("editOfferPositions.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Angebotsdaten bearbeiten");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des Angebotsdaten bearbeiten Fensters!");
			e.printStackTrace();
		}
		EditOfferPositionsController controller = loader.<EditOfferPositionsController>getController();
		controller.initialize(offer);
		stage.show();
	}

	@FXML
	private void createPDF() {
		Offer offer = new Offer();
		if (!table.getSelectionModel().isEmpty()) {
			if (table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("nur einen Kunden markieren!");
			} else {
				offer = table.getSelectionModel().getSelectedItem();
			}
		}
		System.out.println("erstelle pdf fuer angebot " + offer.getId() + ".");
		OfferPDFCreator oc = new OfferPDFCreator(offer);
		oc.createPDF();
	}

	@FXML
	private void searchOffer() {
		if (idField.getText().length() > 0) {
			try {
				OfferDAO odao = new OfferDAO();
				int offerId = Integer.parseInt(idField.getText());
				Offer offer = odao.searchOfferById(offerId);
				list.clear();
				list.add(offer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (customerIdField.getText().length() > 0) {
			try {
				OfferDAO odao = new OfferDAO();

				List<Offer> offerList = odao.findOffersByCustomerId(Integer.valueOf(customerIdField.getText()));
				list.clear();
				list.addAll(offerList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (idField.getText().length() == 0 && customerIdField.getText().length() == 0) {
			try {
				OfferDAO odao = new OfferDAO();
				List<Offer> offerList = odao.selectAllOffers();
				list.clear();
				list.addAll(offerList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else
			return;

	}

	@FXML
	private void createOffer(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("createOffer.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Angebot erstellen");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des Angeboterstellen Fensters!");
			e.printStackTrace();
		}
	}

	@FXML
	private void deleteOffer(ActionEvent event) {
		if (!table.getSelectionModel().isEmpty()) {
			if (table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("Bitte nur ein Produkt markieren!");
			} else {
				Offer getsRemoved = table.getSelectionModel().getSelectedItem();
				try {
					OfferDAO oDAO = new OfferDAO();
					int removed = oDAO.deleteOffer(getsRemoved.getId());
					if (removed == 1) {
						list.remove(getsRemoved);
					}
				} catch (Exception e) {
					e.printStackTrace();
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Angebot löschen nicht möglich!");
					alert.setHeaderText(null);
					alert.setContentText("Angebot:\n" + getsRemoved.toStringForAlert() + "\nLöschen fehlgeschlagen! Für dieses Angebot bestehen Positionen.");
					alert.showAndWait();
					return;
				}
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Angebot gelöscht!");
				alert.setHeaderText(null);
				alert.setContentText("Gelöschtes Angebot:\n" + getsRemoved.toStringForAlert());
				alert.showAndWait();
			}
		}
	}

	@FXML
	private void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}

}
