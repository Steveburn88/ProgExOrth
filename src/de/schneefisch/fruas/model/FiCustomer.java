package de.schneefisch.fruas.model;

public class FiCustomer {

	private int id;
	private String name;

	public FiCustomer() {

	}

	public FiCustomer(int id, String name) {
		this.id = id;
		this.name = name;
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

	@Override
	public String toString() {
		return "FiCustomer [id=" + id + ", name=" + name + "]";
	}

	
}
