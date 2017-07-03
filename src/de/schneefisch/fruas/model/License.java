package de.schneefisch.fruas.model;

import java.sql.Date;

public class License {

	private int id;
	private int customerId;
	private int productId;
	private String key;
	private boolean sold;
	private float discount;
	private Date soldDate;
	private Date endDate;
	private int maintenanceId;

	
	public License() {}
	
	public License( int productId, String key) {		
		this.productId = productId;
		this.key = key;		
	}
	
	
	public License( int productId, String key, boolean sold, float discount, Date soldDate, Date endDate,
			int maintenanceId) {		
		this.productId = productId;
		this.key = key;
		this.sold = sold;
		this.discount = discount;
		this.soldDate = soldDate;
		this.endDate = endDate;
		this.maintenanceId = maintenanceId;
	}
	
	public License(int customerId, int productId, String key, boolean sold, float discount, Date soldDate, Date endDate,
			int maintenanceId) {		
		this.customerId = customerId;
		this.productId = productId;
		this.key = key;
		this.sold = sold;
		this.discount = discount;
		this.soldDate = soldDate;
		this.endDate = endDate;
		this.maintenanceId = maintenanceId;
	}

	public License(int id, int customerId, int productId, String key, boolean sold, float discount, Date soldDate,
			Date endDate, int maintenanceId) {
		
		this.id = id;
		this.customerId = customerId;
		this.productId = productId;
		this.key = key;
		this.sold = sold;
		this.discount = discount;
		this.soldDate = soldDate;
		this.endDate = endDate;
		this.maintenanceId = maintenanceId;
	}

	@Override
	public String toString() {
		return "License [id=" + id + ", customerId=" + customerId + ", productId=" + productId + ", key=" + key
				+ ", sold=" + sold + ", discount=" + discount + ", soldDate=" + soldDate + ", endDate=" + endDate
				+ ", maintenanceId=" + maintenanceId + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isSold() {
		return sold;
	}

	public void setSold(boolean sold) {
		this.sold = sold;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public Date getSoldDate() {
		return soldDate;
	}

	public void setSoldDate(Date soldDate) {
		this.soldDate = soldDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getMaintenanceId() {
		return maintenanceId;
	}

	public void setMaintenanceId(int maintenanceId) {
		this.maintenanceId = maintenanceId;
	}

}
