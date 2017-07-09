package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.ProductDAO;
import de.schneefisch.fruas.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
                Float.parseFloat(price.getText().replaceAll(",", ".").trim()), requirements.getText());
        int updated = 0;
        try {
            ProductDAO cdao = new ProductDAO();
            updated = cdao.updateProduct(updatedProduct);
            System.out.println("affected rows: "+ updated);
        } catch ( Exception e) {
            e.printStackTrace();
        }
        if(updated == 1) {
        	Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Produktdaten aktualisiert!");
    		alert.setHeaderText(null);
    		alert.setContentText("Neue Daten:\n" + updatedProduct.toStringForAlert());
    		alert.showAndWait();
    		Stage stage = (Stage) cancelButton.getScene().getWindow();
    		stage.close();
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
