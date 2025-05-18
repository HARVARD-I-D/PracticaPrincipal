package bussinessUnit.infrastructure.port;

import bussinessUnit.application.domain.model.NewsEvent;
import bussinessUnit.application.domain.model.OilEvent;

import java.util.LinkedList;

public interface LiveBroker {
    void start();
    LinkedList<OilEvent> getRecentOilEvents();
    LinkedList<NewsEvent> getRecentNewsEvents();
}
