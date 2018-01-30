package de.schneefisch.fruas.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainWindowController {

	@FXML private Button createCustomerButton;	
	@FXML private Button searchCustomerButton;
	@FXML private Button searchProductButton;
	@FXML private Button searchOfferButton;
	@FXML private Button searchBillButton;
	@FXML private Button searchDeliveryNoteButton;
	@FXML private Button searchLicenseButton;
	@FXML private Button searchLeasingButton;
	@FXML private Button searchFiCustomerButton;
	@FXML
	private void searchLeasing(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("searchLeasings.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Leasing-Suche");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des Leasing-Suche-Fensters!");
			e.printStackTrace();
		}
		SearchLeasingsController controller = loader.<SearchLeasingsController>getController();		
		stage.show();
	}
	
	@FXML
	private void searchFiCustomer(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("searchFiCustomer.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Firmenkunden");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des Firmenkunden-Suche-Fensters!");
			e.printStackTrace();
		}
		SearchFiCustomerController controller = loader.<SearchFiCustomerController>getController();		
		stage.show();
	}

	
	@FXML
	private void searchCustomer(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("searchCustomer.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Kundensuche");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des Kundensuche Fensters!");
			e.printStackTrace();
		}
	}

	@FXML
	private void createCustomer(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("createCustomer.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Neuer Kunde");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des Neuer Kunde Fensters!");
			e.printStackTrace();
		}

	}
	
	@FXML
	private void searchProduct(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("searchProducts.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Produktsuche");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des produktsuche Fensters!");
			e.printStackTrace();
		}
	}

	@FXML
	private void searchMaintenance(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("searchMaintenance.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Maintenancesuche");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des Maintenancesuche Fensters!");
			e.printStackTrace();
		}
	}
	
	@FXML
	private void searchOffer(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("searchOffers.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Angebotssuche");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des angebotssuche Fensters!");
			e.printStackTrace();
		}
	}
	
	@FXML
	private void searchBill(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("searchBills.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Rechnungssuche");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des rechnungssuche Fensters!");
			e.printStackTrace();
		}
	}
	
	@FXML
	private void searchDeliveryNote(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("searchDeliveryNotes.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Lieferscheinsuche");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des lieferscheinsuche Fensters!");
			e.printStackTrace();
		}
	}
	@FXML
	private void searchLicense(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("searchLicenses.fxml"));
			Stage stage = new Stage();
			stage.setTitle("LizenzSuche");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			System.out.println("Fehler beim Oeffnen des lizenzsuche Fensters!");
			e.printStackTrace();
		}
	}

}
