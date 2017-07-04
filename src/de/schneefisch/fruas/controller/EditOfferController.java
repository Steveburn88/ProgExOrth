package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.OfferPositionDAO;
import de.schneefisch.fruas.database.ProductDAO;
import de.schneefisch.fruas.model.Offer;
import de.schneefisch.fruas.model.OfferPosition;
import de.schneefisch.fruas.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class EditOfferController {

    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button refreshButton;
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

    private Offer offer;

    void initialize(Offer offer) {
        this.offer = offer;
        list.clear();
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
        table.setItems(list);
    }

    @FXML
    private void addPos() {
        Offer offer = this.offer;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("createOfferPosition.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Neue AngebotsPosition");
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            System.out.println("Fehler beim Oeffnen des angebotsPositions Fensters!");
            e.printStackTrace();
        }
        CreateOfferPositionController controller = loader.<CreateOfferPositionController>getController();
        controller.initData(offer);
        stage.show();
    }

    @FXML
    private void deletePos() {
        if(!table.getSelectionModel().isEmpty()) {
            if(table.getSelectionModel().getSelectedItems().size() > 1) {
                System.out.println("Bitte nur eine Angebotsposition markieren!");
            } else {
                OfferPosition getsRemoved = table.getSelectionModel().getSelectedItem();
                try {
                    OfferPositionDAO odao = new OfferPositionDAO();
                    int offPoId = getsRemoved.getId();
                    int removed = odao.deleteOfferPosition(offPoId);
                    if(removed == 1 ) {
                        list.remove(getsRemoved);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    private void refresh() {
        Stage stage = (Stage) refreshButton.getScene().getWindow();
        //table.refresh();
        this.initialize(offer);
        stage.show();
    }

    @FXML
    private void cancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}
