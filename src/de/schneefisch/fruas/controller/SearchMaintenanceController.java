package de.schneefisch.fruas.controller;


import de.schneefisch.fruas.database.MaintenanceDAO;
import de.schneefisch.fruas.model.Maintenance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class SearchMaintenanceController implements Initializable {
    @FXML
    private Button searchButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;
    @FXML private Button showButton;
    @FXML private Button cancelButton;
    @FXML private TextField idField;

    @FXML private TableView<Maintenance> table;
    @FXML private TableColumn<Maintenance, Integer> id;
    @FXML private TableColumn<Maintenance, String> info;
    @FXML private TableColumn<Maintenance, Float> price;
    @FXML private TableColumn<Maintenance, Date> start;
    @FXML private TableColumn<Maintenance, Date> end;

    private ObservableList<Maintenance> list =  FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            MaintenanceDAO mdao = new MaintenanceDAO();
            List<Maintenance> maintenanceList = mdao.selectAllMaintenances();
			/*DBConnector dbc = new DBConnector();
			List<Customer> customerList = dbc.selectAllCustomers();*/
            maintenanceList.stream().forEach(System.out::println);
            list.addAll(maintenanceList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        id.setCellValueFactory(new PropertyValueFactory<Maintenance, Integer>("id"));
        info.setCellValueFactory(new PropertyValueFactory<Maintenance, String>("info"));
        price.setCellValueFactory(new PropertyValueFactory<Maintenance, Float>("price"));
        start.setCellValueFactory(new PropertyValueFactory<Maintenance, Date>("start"));
        end.setCellValueFactory(new PropertyValueFactory<Maintenance, Date>("end"));
        table.setItems(list);
    }

    @FXML
    private void createMaintenance(ActionEvent event) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("createMaintenance.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Neues Maintenance");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Fehler beim Öffnen des Neuen Maintenance Fensters!");
            e.printStackTrace();
        }

    }

    @FXML
    private void searchMaintenance() {
        if(idField.getText().length() > 0) {
            try {
                MaintenanceDAO mdao = new MaintenanceDAO();
                int maintenanceId = Integer.parseInt(idField.getText());
                Maintenance maintenance = mdao.searchMaintenanceById(maintenanceId);
                list.clear();
                list.add(maintenance);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(idField.getText().length() == 0) {
            try {
                MaintenanceDAO mdao = new MaintenanceDAO();
                List<Maintenance> maintenanceList = mdao.selectAllMaintenances();
                list.clear();
                list.addAll(maintenanceList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else return;
    }

    @FXML
    private void editMaintenance() {
        if(!table.getSelectionModel().isEmpty()) {
            if(table.getSelectionModel().getSelectedItems().size() > 1) {
                System.out.println("Bitte nur einen Maintenance markieren!");
            } else {
                Maintenance getsEdited = table.getSelectionModel().getSelectedItem();
                showEditMaintenance(getsEdited);
            }
        }
    }

    private void showEditMaintenance(Maintenance maintenance) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("editMaintenance.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Maintenancedaten bearbeiten");
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            System.out.println("Fehler beim Öffnen des Maintenancedaten bearbeiten Fensters!");
            e.printStackTrace();
        }
        EditMaintenanceController controller = loader.<EditMaintenanceController>getController();
        controller.initData(maintenance);
        stage.show();
    }

    @FXML
    private void deleteMaintenance() {
        if(!table.getSelectionModel().isEmpty()) {
            if(table.getSelectionModel().getSelectedItems().size() > 1) {
                System.out.println("Bitte nur einen Maintenance markieren!");
            } else {
                Maintenance getsRemoved = table.getSelectionModel().getSelectedItem();
                try {
                    MaintenanceDAO mdao = new MaintenanceDAO();
                    int maintenanceId = getsRemoved.getId();
                    int removed = mdao.deleteMaintenance(maintenanceId);
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
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }



















}
