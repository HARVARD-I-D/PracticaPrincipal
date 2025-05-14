package org.example;
import jakarta.jms.*;

public interface OilStore {
    void save(OilPrice oilPrice);
}
