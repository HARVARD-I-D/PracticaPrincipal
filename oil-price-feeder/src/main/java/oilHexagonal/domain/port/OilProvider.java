package oilHexagonal.domain.port;

import oilHexagonal.domain.model.OilPrice;
import java.util.List;

public interface OilProvider {
    List<OilPrice> provide();
}
