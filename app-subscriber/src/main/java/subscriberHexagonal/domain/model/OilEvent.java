package subscriberHexagonal.domain.model;

import java.time.Instant;

public class OilEvent {
    private Instant ts;
    private double value;
    private String type;
    private String ss;


    public OilEvent(Instant ts, Double value, String type, String ss) {
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