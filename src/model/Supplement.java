package model;

import java.io.Serializable;

public class Supplement implements Serializable {
    private int id;
    private String name;
    private double mass;
    private int amount;
    private String source;
    private int price;

    public Supplement(int id, String name, double mass, int amount, String source, int price) {
        this.id = id;
        this.name = name;
        this.mass = mass;
        this.amount = amount;
        this.source = source;
        this.price = price;
    }

    public Supplement() {
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

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Supplement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mass=" + mass +
                ", amount=" + amount +
                ", source='" + source + '\'' +
                ", price=" + price +
                '}';
    }
}
