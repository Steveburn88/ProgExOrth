package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.ProductDAO;
import de.schneefisch.fruas.model.Product;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class EditProductController {

    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField name;
    @FXML
    private TextField version;
    @FXML
    private TextField price;
    @FXML
    private TextField requirements;
    @FXML
    private Product product;

    void initialize() {
    };

    void initData(Product product) {
        this.product = product;
        System.out.println(product.getId());
        name.setText(product.getName());
        version.setText(product.getVersion());
        price.setText(String.valueOf(product.getPrice()));
        requirements.setText(product.getRequirements());
    }


    @FXML
    private void saveProduct() {
        Product updatedProduct = new Product(product.getId(), name.getText(), version.getText(),
                Float.parseFloat(price.getText().trim()), requirements.getText());

        try {
            ProductDAO cdao = new ProductDAO();
            int updated = cdao.updateProduct(updatedProduct);
            System.out.println("affected rows: "+ updated);
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
