package org.example;

import java.time.Instant;

public class OilPrice {
    Instant ts;
    String type;
    Double value;

    public OilPrice(Instant ts, Double value, String type) {
        this.ts = ts;
        this.value = value;
        this.type = type;
    }

    public Instant getTs() {
        return ts;
    }

    public String getTsAsString(){
        return String.valueOf(ts);
    }

    public Double getValue() {
        return value;
    }

    public String getType() {
        return type;
    }
}
