package oilFeeder.infrastructure.port;

import oilFeeder.application.domain.model.OilPrice;
import java.util.List;

public interface OilProvider {
    List<OilPrice> provide();
}
