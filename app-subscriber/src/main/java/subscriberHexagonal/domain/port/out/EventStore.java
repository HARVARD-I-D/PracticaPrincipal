package subscriberHexagonal.domain.port.out;

import subscriberHexagonal.domain.model.NewsEvent;
import subscriberHexagonal.domain.model.OilEvent;

public interface EventStore {
    void storeOil(OilEvent oilEvent);
    void storeNews(NewsEvent newsEvent);
}
