package de.schneefisch.fruas.model;

public class FiCustomer {

	private int id;
	private String name;

	public FiCustomer() {

	}

	public FiCustomer(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
