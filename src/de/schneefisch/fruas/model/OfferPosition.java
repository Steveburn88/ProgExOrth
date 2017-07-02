package de.schneefisch.fruas.model;

public class OfferPosition {

	private int id;
	private int offerId;
	private int count;
	private int productId;
	private int leasingId;
	private int maintenanceId;

	public OfferPosition() {

	}

	public OfferPosition(int offerId, int count, int productId) {

		this.offerId = offerId;
		this.count = count;
		this.productId = productId;
	}

	public OfferPosition(int id, int offerId, int count, int productId) {
		this.id = id;
		this.offerId = offerId;
		this.count = count;
		this.productId = productId;
	}

	public OfferPosition(int id, int offerId, int count, int productId, int leasingId, int maintenanceId) {
		this.id = id;
		this.offerId = offerId;
		this.count = count;
		this.productId = productId;
		this.leasingId = leasingId;
		this.maintenanceId = maintenanceId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOfferId() {
		return offerId;
	}

	public void setOfferId(int offerId) {
		this.offerId = offerId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getLeasingId() {
		return leasingId;
	}

	public void setLeasingId(int leasingId) {
		this.leasingId = leasingId;
	}

	public int getMaintenanceId() {
		return maintenanceId;
	}

	public void setMaintenanceId(int maintenanceId) {
		this.maintenanceId = maintenanceId;
	}

	@Override
	public String toString() {
		return "OfferPosition [id=" + id + ", offerId=" + offerId + ", count=" + count + ", productId=" + productId
				+ ", leasingId=" + leasingId + ", maintenanceId=" + maintenanceId + "]";
	}

}
