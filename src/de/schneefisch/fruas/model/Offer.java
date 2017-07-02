package de.schneefisch.fruas.model;

import java.sql.Date;

public class Offer {

	private int id;
	private int customerId;
	private Date validity;

	public Offer() {
		
	}
	
	public Offer(int customerId, Date validity) {
		this.customerId = customerId;
		this.validity = validity;
	}
	
	public Offer(int id, int customerId, Date validity) {
		this.id = id;
		this.customerId = customerId;
		this.validity = validity;
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

	public Date getValidity() {
		return validity;
	}

	public void setValidity(Date validity) {
		this.validity = validity;
	}

	@Override
	public String toString() {
		return "Offer [id=" + id + ", customerId=" + customerId + ", validity=" + validity + "]";
	}

}
