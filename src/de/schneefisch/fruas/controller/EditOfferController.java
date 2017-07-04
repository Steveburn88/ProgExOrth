package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.OfferPositionDAO;
import de.schneefisch.fruas.database.ProductDAO;
import de.schneefisch.fruas.model.Offer;
import de.schneefisch.fruas.model.OfferPosition;
import de.schneefisch.fruas.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class EditOfferController {

    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button cancelButton;

    private ObservableList<OfferPosition> list = FXCollections.observableArrayList();
    private ObservableList<Product> listprod = FXCollections.observableArrayList();

    @FXML
    private TableView<OfferPosition> table;
    @FXML
    private TableColumn<OfferPosition, Integer> posId;
    @FXML
    private TableColumn<OfferPosition, Integer> productId;
    @FXML
    private TableColumn<OfferPosition, Integer> amount;
    @FXML
    private TableColumn<Product, Float> priceUnit;
    @FXML
    private TableColumn<OfferPosition, Float> priceSum;

    void initialize(Offer offer) {
        try {
            OfferPositionDAO odao = new OfferPositionDAO();
            List<OfferPosition> offerPosList = odao.searchOfferPositionsByOfferId(offer.getId());
            offerPosList.stream().forEach(System.out::println);
            list.addAll(offerPosList);

            ProductDAO pdao = new ProductDAO();
            for (OfferPosition op : list) {
                listprod.add(pdao.searchProductById(op.getProductId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        posId.setCellValueFactory(new PropertyValueFactory<OfferPosition, Integer>("id"));
        productId.setCellValueFactory(new PropertyValueFactory<OfferPosition, Integer>("productId"));
        amount.setCellValueFactory(new PropertyValueFactory<OfferPosition, Integer>("count"));
        //priceUnit.setCellValueFactory(new PropertyValueFactory<Product, Float>("priceUnit"));
        //priceSum.setCellValueFactory(new PropertyValueFactory<OfferPosition, Float>("priceSum"));
        table.setItems(list);
        //table.setItems(listprod);
    }

    @FXML
    private void addPos() {
        //TODO: implement
    }

    @FXML
    private void deletePos() {
        //TODO: implement
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
