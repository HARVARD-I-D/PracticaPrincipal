package oilHexagonal.domain.port;

import oilHexagonal.domain.model.OilPrice;

public interface OilStore {
    void save(OilPrice oilPrice);
}
