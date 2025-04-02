package org.example;

public interface OilStore {
    void save(OilPrice oilPrice);
    OilPrice load();
}
