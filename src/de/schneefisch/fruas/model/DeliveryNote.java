package de.schneefisch.fruas.model;

import java.sql.Date;

public class DeliveryNote {

	private int id;
	private Date date;

	public DeliveryNote(){}
	
	public DeliveryNote(Date date) {
		this.date = date;
	}
	
	public DeliveryNote(int id, Date date) {
		this.id = id;
		this.date = date;
	}

	@Override
	public String toString() {
		return "DeliveryNote [id=" + id + ", date=" + date + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
