package de.schneefisch.fruas.model;

import java.sql.Date;

public class Maintenance {

    private int id;
    private String info;
    private float price;
    private Date start;
    private Date end;

    public Maintenance() {}

    public Maintenance(String info, float price, Date start, Date end) {
        this.info = info;
        this.price = price;
        this.start = start;
        this.end = end;
    }

    public Maintenance(int id, String info, float price, Date start, Date end) {
        this.id = id;
        this.info = info;
        this.price = price;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Maintenance [id=" + id + ", infoMaintenance=" + info + ", price=" + price
                + ", startDate=" + start.toString() + ", endDate=" + end.toString() + "]";
    }
}
