package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.OfferPositionDAO;
import de.schneefisch.fruas.database.ProductDAO;
import de.schneefisch.fruas.model.Customer;
import de.schneefisch.fruas.model.Offer;
import de.schneefisch.fruas.model.OfferPosition;
import de.schneefisch.fruas.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CreateOfferPositionController implements Initializable {

	@FXML private TextField productIdField;
	@FXML private TextField amountField;
	@FXML private ComboBox<String> productBox;
	@FXML private Button createOfferPositionButton;
	@FXML private Button cancelButton;	
	@FXML private Offer offer;	
	private ObservableList<String> productList = FXCollections.observableArrayList();
	public void setOffer(Offer offer) {
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
			e.printStackTrace();
		}
		
	}
	
	@FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ProductDAO pDAO = new ProductDAO();
		try {
			List<Product> pList = pDAO.selectAllProducts();
			System.out.println(pList);
			List<String> productStringList = pList.stream().map(p -> p.toStringForList()).collect(Collectors.toList());
			productList.addAll(productStringList);
			productBox.getItems().addAll(productList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		productBox.setOnAction(e -> productIdField.setText(productBox.getValue().substring(productBox.getValue().indexOf("[")+1,productBox.getValue().indexOf("]") )));
	}

	
}
