package org.example;

public class WTI {
    String date;
    Double value;

    public WTI(String date, Double value) {
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
