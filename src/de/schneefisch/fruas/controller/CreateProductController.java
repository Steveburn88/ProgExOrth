package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.ProductDAO;
import de.schneefisch.fruas.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateProductController {
    @FXML
    private TextField name;
    @FXML
    private TextField version;
    @FXML
    private TextField price;
    @FXML
    private TextField requirements;
    @FXML
    private Button createCustomerButton;
    @FXML
    private Button cancelButton;

    @FXML
    private void createProduct(ActionEvent event) {
        Product product = new Product(name.getText(), version.getText(), Float.parseFloat(price.getText().trim()), requirements.getText());
        try {
            ProductDAO pdao = new ProductDAO();
            pdao.insertProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
