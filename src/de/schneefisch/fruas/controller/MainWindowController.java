package de.schneefisch.fruas.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainWindowController {

	@FXML private Button createCustomerButton;	
	@FXML private Button searchCustomerButton;
	@FXML private Button searchProductButton;
	@FXML private Button searchOfferButton;
	@FXML private Button searchBillButton;
	@FXML private Button searchDeliveryNoteButton;

	
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
			System.out.println("Fehler beim öffnen des Kundensuche Fensters!");
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
			System.out.println("Fehler beim öffnen des Neuer Kunde Fensters!");
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
			System.out.println("Fehler beim öffnen des produktsuche Fensters!");
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
			System.out.println("Fehler beim öffnen des angebotssuche Fensters!");
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
			System.out.println("Fehler beim öffnen des rechnungssuche Fensters!");
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
			System.out.println("Fehler beim öffnen des lieferscheinsuche Fensters!");
			e.printStackTrace();
		}
	}

}
