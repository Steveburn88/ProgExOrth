package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.database.ProductDAO;
import de.schneefisch.fruas.model.Product;

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
import java.util.List;
import java.util.ResourceBundle;

public class SearchProductController implements Initializable {

	@FXML private Button createButton;
	@FXML private Button searchButton;
	@FXML private Button editButton;
	@FXML private Button deleteButton;
	@FXML private Button cancelButton;
	@FXML private TextField idField;
	@FXML private TextField nameField;

	@FXML private TableView<Product> table;
	@FXML private TableColumn<Product, Integer> id;
	@FXML private TableColumn<Product, String> name;
	@FXML private TableColumn<Product, String> version;
	@FXML private TableColumn<Product, Float> price;
	@FXML private TableColumn<Product, String> systemRequirements;


	private ObservableList<Product> list =  FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			ProductDAO cdao = new ProductDAO();
			List<Product> customerList = cdao.selectAllProducts();
			/*DBConnector dbc = new DBConnector();
			List<Customer> customerList = dbc.selectAllCustomers();*/
			customerList.stream().forEach(System.out::println);
			list.addAll(customerList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		id.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
		name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
		version.setCellValueFactory(new PropertyValueFactory<Product, String>("version"));
		price.setCellValueFactory(new PropertyValueFactory<Product, Float>("price"));
		systemRequirements.setCellValueFactory(new PropertyValueFactory<Product, String>("requirements"));
		table.setItems(list);

	}

	@FXML
	private void createProduct(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getClassLoader().getResource("createProduct.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Neues Product");
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			System.out.println("Fehler beim Öffnen des Neuen Produkt Fensters!");
			e.printStackTrace();
		}

	}

	@FXML
	private void searchProduct() {
		if(idField.getText().length() > 0) {
			try {
				ProductDAO pdao = new ProductDAO();
				int prodId = Integer.parseInt(idField.getText());
				Product product = pdao.searchProductById(prodId);
				list.clear();
				list.add(product);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (nameField.getText().length() > 0) {
			try {
				ProductDAO pdao = new ProductDAO();
				List<Product> customerList = pdao.searchProductByName(nameField.getText());
				list.clear();
				list.addAll(customerList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(idField.getText().length() == 0 && nameField.getText().length() == 0) {
			try {
				ProductDAO pdao = new ProductDAO();
				List<Product> productList = pdao.selectAllProducts();
				list.clear();
				list.addAll(productList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else return;

	}

	@FXML
	private void editProduct() {
		if(!table.getSelectionModel().isEmpty()) {
			if(table.getSelectionModel().getSelectedItems().size() > 1) {
				System.out.println("Bitte nur ein Produkt markieren!");
			} else {
				Product getsEdited = table.getSelectionModel().getSelectedItem();
				showEditProduct(getsEdited);
			}
		}
	}

	private void showEditProduct(Product product) {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("editProduct.fxml"));
		Stage stage = new Stage();
		stage.setTitle("Produktdaten bearbeiten");
		try {
			stage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			System.out.println("Fehler beim Öffnen des Produktdaten bearbeiten Fensters!");
			e.printStackTrace();
		}
		EditProductController controller = loader.<EditProductController>getController();
		controller.initData(product);
		stage.show();
	}


	@FXML
	private void cancel(ActionEvent event) {
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
}
