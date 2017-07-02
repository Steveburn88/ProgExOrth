package de.schneefisch.fruas.controller;



import de.schneefisch.fruas.database.MaintenanceDAO;
import de.schneefisch.fruas.database.OfferDAO;
import de.schneefisch.fruas.database.ProductDAO;
import de.schneefisch.fruas.model.Maintenance;
import de.schneefisch.fruas.model.Offer;
import de.schneefisch.fruas.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;

public class CreateOfferController {

	@FXML
    private TextField customerId;
    @FXML
    private TextField validity;
    @FXML
    private Button createOfferButton;
    @FXML
    private Button cancelButton;

    @FXML
    private void createOffer(ActionEvent event) {
    	 Offer offer = new Offer(Integer.valueOf(customerId.getText()) , Date.valueOf(validity.getText()));
         try {
             OfferDAO odao = new OfferDAO();
             odao.insertOffer(offer);
         } catch (Exception e) {
             e.printStackTrace();
         }
         Stage stage = (Stage) createOfferButton.getScene().getWindow();
         stage.close();
    }


    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}

