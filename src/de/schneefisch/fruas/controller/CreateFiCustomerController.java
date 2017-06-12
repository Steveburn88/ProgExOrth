package de.schneefisch.fruas.controller;

import de.schneefisch.fruas.model.Customer;
import javafx.fxml.FXML;

public class CreateFiCustomerController {

@FXML private Customer customer;
	
	void initialize(){};
	void initData(Customer customer) {
		this.customer = customer;
		System.out.println("in init:" + customer);
	}
	
}
