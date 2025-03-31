package org.example;

public class OilPrice {
    String date;
    Double value;

    public OilPrice(String date, Double value) {
        this.date = date;
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public Double getValue() {
        return value;
    }
}
