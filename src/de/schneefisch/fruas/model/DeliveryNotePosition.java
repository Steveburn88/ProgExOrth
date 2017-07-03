package de.schneefisch.fruas.model;

public class DeliveryNotePosition {
	private int id;
	private int deliveryNoteId;
	private int licenseId;

	public DeliveryNotePosition() {}
	
	public DeliveryNotePosition( int deliveryNoteId, int licenseId) {		
		this.deliveryNoteId = deliveryNoteId;
		this.licenseId = licenseId;
	}
	
	
	public DeliveryNotePosition(int id, int deliveryNoteId, int licenseId) {
		this.id = id;
		this.deliveryNoteId = deliveryNoteId;
		this.licenseId = licenseId;
	}

	@Override
	public String toString() {
		return "DeliveryNotePosition [id=" + id + ", deliveryNoteId=" + deliveryNoteId + ", licenseId=" + licenseId
				+ "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDeliveryNoteId() {
		return deliveryNoteId;
	}

	public void setDeliveryNoteId(int deliveryNoteId) {
		this.deliveryNoteId = deliveryNoteId;
	}

	public int getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(int licenseId) {
		this.licenseId = licenseId;
	}

}
