package de.schneefisch.fruas.model;

public class Customer {

	private int id;
	private int fiKuId;
	private int locationId;
	private Salutation salutation;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String position;
	private String department;
	private String roomNumber;
	private String buildingNumber;
	private String faxNumber;

	public Customer() {

	}
	
	public Customer(int id, int fiKuId, int locationId, Salutation salutation, String firstName, String lastName, String phoneNumber,
			String email, String position, String department, String roomNumber, String buildingNumber,
			String faxNumber) {
		this.id = id;
		this.fiKuId = fiKuId;
		this.locationId = locationId;
		this.salutation = salutation;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.position = position;
		this.department = department;
		this.roomNumber = roomNumber;
		this.buildingNumber = buildingNumber;
		this.faxNumber = faxNumber;
	}

	public Customer(int fiKuId, int locationId, Salutation salutation, String firstName, String lastName, String phoneNumber,
			String email, String position, String department, String roomNumber, String buildingNumber,
			String faxNumber) {

		this.fiKuId = fiKuId;
		this.locationId = locationId;
		this.salutation = salutation;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.position = position;
		this.department = department;
		this.roomNumber = roomNumber;
		this.buildingNumber = buildingNumber;
		this.faxNumber = faxNumber;
	}
	
	public Customer(int id, Salutation salutation, String firstName, String lastName, String phoneNumber, String email,
			String position, String department, String roomNumber, String buildingNumber, String faxNumber) {
		this.id = id;
		this.salutation = salutation;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.position = position;
		this.department = department;
		this.roomNumber = roomNumber;
		this.buildingNumber = buildingNumber;
		this.faxNumber = faxNumber;
	}

	public Customer(Salutation salutation, String firstName, String lastName, String phoneNumber, String email,
			String position, String department, String roomNumber, String buildingNumber, String faxNumber) {
		this.salutation = salutation;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.position = position;
		this.department = department;
		this.roomNumber = roomNumber;
		this.buildingNumber = buildingNumber;
		this.faxNumber = faxNumber;
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

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public Salutation getSalutation() {
		return salutation;
	}

	public void setSalutation(Salutation salutation) {
		this.salutation = salutation;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getBuildingNumber() {
		return buildingNumber;
	}

	public void setBuildingNumber(String buildingNumber) {
		this.buildingNumber = buildingNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", fiKuId=" + fiKuId + ", locationId=" + locationId + ", salutation=" + salutation
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber + ", email="
				+ email + ", position=" + position + ", department=" + department + ", roomNumber=" + roomNumber
				+ ", buildingNumber=" + buildingNumber + ", faxNumber=" + faxNumber + "]";
	}

}
