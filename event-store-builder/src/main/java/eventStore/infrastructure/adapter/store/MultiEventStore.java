package eventStore.infrastructure.adapter.store;

import eventStore.application.domain.model.NewsEvent;
import eventStore.application.domain.model.OilEvent;
import eventStore.infrastructure.port.EventStore;

public class MultiEventStore implements EventStore {
    private static final String url = "jdbc:sqlite:events.db";
    //TODO Guardar Eventos en Ficheros


    @Override
    public void storeOil(OilEvent oilEvent) {

    }

    @Override
    public void storeNews(NewsEvent newsEvent) {

    }
}
