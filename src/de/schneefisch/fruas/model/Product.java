package de.schneefisch.fruas.model;

public class Product {

    private int id;
    private String name;
    private String version;
    private float price;
    private String requirements;

    public Product() {}

    public Product(String name, String version, float price, String requirements) {
        this.name = name;
        this.version = version;
        this.price = price;
        this.requirements = requirements;
    }

    public Product(int id, String name, String version, float price, String requirements) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.price = price;
        this.requirements = requirements;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", nameProduct=" + name + ", versionNumberProduct=" + version
                + ", price=" + price + ", requirements=" + requirements + "]";
    }
}
