package de.schneefisch.fruas.model;

public class Location {

	private int id;
	private int fiKuId;
	private int postalCode;
	private String city;
	private String postBox;
	private String street;
	private String houseNumber;

	public Location() {

	}

	public Location(int fiKuId, int postalCode, String city, String postBox, String street, String houseNumber) {
		this.fiKuId = fiKuId;
		this.postalCode = postalCode;
		this.city = city;
		this.postBox = postBox;
		this.street = street;
		this.houseNumber = houseNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFiKuId() {
		return fiKuId;
	}

	public void setFiKuId(int fiKuId) {
		this.fiKuId = fiKuId;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostBox() {
		return postBox;
	}

	public void setPostBox(String postBox) {
		this.postBox = postBox;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

}
