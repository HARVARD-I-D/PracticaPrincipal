package eventStore.infrastructure.port;

import eventStore.application.domain.model.NewsEvent;
import eventStore.application.domain.model.OilEvent;

import java.util.ArrayList;

public interface EventHandler {
    void start();
    ArrayList<OilEvent> handleOil();
    ArrayList<NewsEvent> handleNews();
}
