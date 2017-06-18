package de.schneefisch.fruas.model;

public class Location {

	private int id;
	private int fiKuId;
	private String postalCode;
	private String city;
	private String postBox;
	private String street;
	private String houseNumber;

	public Location() {

	}

	public Location(int id, int fiKuId, String postalCode, String city, String postBox, String street, String houseNumber) {
		this.id = id;
		this.fiKuId = fiKuId;
		this.postalCode = postalCode;
		this.city = city;
		this.postBox = postBox;
		this.street = street;
		this.houseNumber = houseNumber;
	}
	
	public Location(String postalCode, String city, String postBox, String street, String houseNumber) {
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

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
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

	@Override
	public String toString() {
		return "Location [id=" + id + ", fiKuId=" + fiKuId + ", postalCode=" + postalCode + ", city=" + city
				+ ", postBox=" + postBox + ", street=" + street + ", houseNumber=" + houseNumber + "]";
	}

}
