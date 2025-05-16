package oilFeeder.infrastructure.port;

import oilFeeder.application.domain.model.OilPrice;

public interface OilStore {
    void save(OilPrice oilPrice);
}
