package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.OfferPositionDAO;
import de.schneefisch.fruas.model.Offer;
import de.schneefisch.fruas.model.OfferPosition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class CreateOfferPositionController {

	@FXML private TextField productIdField;
	@FXML private TextField amountField;
	
	@FXML private Button createOfferPositionButton;
	@FXML private Button cancelButton;
	
	@FXML private Offer offer;
	
	void initialize(){};
	void initData(Offer offer) {
		this.offer = offer;		
	}
	
	@FXML
	private void createOfferPosition(ActionEvent event) {
		OfferPosition op = new OfferPosition(offer.getId(), Integer.valueOf(amountField.getText()), Integer.valueOf(productIdField.getText()));
		System.out.println(op);
		OfferPositionDAO opDao = new OfferPositionDAO();
		try {
			opDao.insertOfferPosition(op);
			Stage stage = (Stage) createOfferPositionButton.getScene().getWindow();
			stage.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
