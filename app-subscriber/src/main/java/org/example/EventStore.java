package org.example;

import java.util.List;

public interface EventStore {
    void storeOil(String oilPrice);
    void storeNews(String news);
}
