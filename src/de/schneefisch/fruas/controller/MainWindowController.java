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

}
