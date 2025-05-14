package org.example;

import java.io.Serializable;
import java.time.Instant;

public class OilPrice implements Serializable {
    private final Instant ts;
    private final String ss;
    private final String type;
    private final Double value;

    public OilPrice(Instant ts, String ss, Double value, String type) {
        this.ts = ts;
        this.ss = ss;
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

    public String getSs() {
        return ss;
    }
}
