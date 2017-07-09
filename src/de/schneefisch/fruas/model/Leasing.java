package de.schneefisch.fruas.model;

import java.sql.Date;

public class Leasing {

	private int id;
	private int customerId;
	private int productId;
	private Date startDate;
	private Date firstBillDate;
	private Date recentBillDate;
	private Date dueBillDate;
	private int numberOfBills;
	private float billPayment;

	public Leasing() {

	}

	public Leasing(int id, int customerId, int productId, Date startDate, Date firstBillDate, Date recentBillDate,
			Date dueBillDate, int numberOfBills, float billPayment) {
		this.id = id;
		this.customerId = customerId;
		this.productId = productId;
		this.startDate = startDate;
		this.firstBillDate = firstBillDate;
		this.recentBillDate = recentBillDate;
		this.dueBillDate = dueBillDate;
		this.numberOfBills = numberOfBills;
		this.billPayment = billPayment;
	}

	public Leasing(int customerId, int productId, Date startDate, Date firstBillDate, Date recentBillDate,
			Date dueBillDate, int numberOfBills, float billPayment) {
		super();
		this.customerId = customerId;
		this.productId = productId;
		this.startDate = startDate;
		this.firstBillDate = firstBillDate;
		this.recentBillDate = recentBillDate;
		this.dueBillDate = dueBillDate;
		this.numberOfBills = numberOfBills;
		this.billPayment = billPayment;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getFirstBillDate() {
		return firstBillDate;
	}

	public void setFirstBillDate(Date firstBillDate) {
		this.firstBillDate = firstBillDate;
	}

	public Date getRecentBillDate() {
		return recentBillDate;
	}

	public void setRecentBillDate(Date recentBillDate) {
		this.recentBillDate = recentBillDate;
	}

	public Date getDueBillDate() {
		return dueBillDate;
	}

	public void setDueBillDate(Date dueBillDate) {
		this.dueBillDate = dueBillDate;
	}

	public int getNumberOfBills() {
		return numberOfBills;
	}

	public void setNumberOfBills(int numberOfBills) {
		this.numberOfBills = numberOfBills;
	}

	public float getBillPayment() {
		return billPayment;
	}

	public void setBillPayment(float billPayment) {
		this.billPayment = billPayment;
	}

	@Override
	public String toString() {
		return "Leasing [id=" + id + ", customerId=" + customerId + ", productId=" + productId + ", startDate="
				+ startDate + ", firstBillDate=" + firstBillDate + ", recentBillDate=" + recentBillDate
				+ ", dueBillDate=" + dueBillDate + ", numberOfBills=" + numberOfBills + ", billPayment=" + billPayment
				+ "]";
	}

	
	
}
