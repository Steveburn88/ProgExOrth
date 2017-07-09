package de.schneefisch.fruas.model;
import java.sql.Date;

public class Bill {

    private int id;
    //private int paid;
    private boolean paid;
    private float price;
    private int deliveryNoteId;

    public Bill() {

    }

    public Bill(boolean paid, float price, int delivery_note_id) {
        this.paid = paid;
        this.price = price;
        this.deliveryNoteId = delivery_note_id;
    }

    public Bill(int id, boolean paid, float price, int delivery_note_id) {
        this.id = id;
        this.paid = paid;
        this.price = price;
        this.deliveryNoteId = delivery_note_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDeliveryNoteId() {
        return deliveryNoteId;
    }

    public void setDeliveryNoteId(int delivery_note_id) {
        this.deliveryNoteId = delivery_note_id;
    }

    @Override
    public String toString() {
        return "Bill [id="+id+", paid="+paid +", price="+price+", delivery note id="+deliveryNoteId+"]";
    }
}
