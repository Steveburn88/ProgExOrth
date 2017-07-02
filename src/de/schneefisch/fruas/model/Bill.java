package de.schneefisch.fruas.model;
import java.sql.Date;

public class Bill {

    private int id;
    //private int paid;
    private Date paid;
    private float price;
    private int delivery_note_id;

    public Bill() {

    }

    public Bill(Date paid, float price, int delivery_note_id) {
        this.paid = paid;
        this.price = price;
        this.delivery_note_id = delivery_note_id;
    }

    public Bill(int id, Date paid, float price, int delivery_note_id) {
        this.id = id;
        this.paid = paid;
        this.price = price;
        this.delivery_note_id = delivery_note_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPaid() {
        return paid;
    }

    public void setPaid(Date paid) {
        this.paid = paid;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDelivery_note_id() {
        return delivery_note_id;
    }

    public void setDelivery_note_id(int delivery_note_id) {
        this.delivery_note_id = delivery_note_id;
    }

    @Override
    public String toString() {
        return "Bill [id="+id+", paid="+paid.toString()+", price="+price+", delivery note id="+delivery_note_id+"]";
    }
}
