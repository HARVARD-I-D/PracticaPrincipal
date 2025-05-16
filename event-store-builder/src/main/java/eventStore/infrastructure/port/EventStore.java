package eventStore.infrastructure.port;

import eventStore.application.domain.model.NewsEvent;
import eventStore.application.domain.model.OilEvent;

public interface EventStore {
    void storeOil(OilEvent oilEvent);
    void storeNews(NewsEvent newsEvent);
}
