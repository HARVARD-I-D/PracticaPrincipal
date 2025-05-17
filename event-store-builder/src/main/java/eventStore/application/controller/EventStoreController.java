package eventStore.application.controller;

import eventStore.application.domain.model.NewsEvent;
import eventStore.application.domain.model.OilEvent;
import eventStore.infrastructure.port.EventHandler;
import eventStore.infrastructure.port.EventStore;
import java.util.ArrayList;

public class EventStoreController {
    private final EventHandler eventHandler;
    private final EventStore eventStore;

    public EventStoreController(EventHandler eventReceiver, EventStore eventStore){
        this.eventHandler = eventReceiver;
        this.eventStore = eventStore;
    }

    public void run() {
        eventHandler.start();
        System.out.println("Esperando eventos...");

        while (true) {
            ArrayList<OilEvent> oilEvents = eventHandler.handleOil();
            if (!oilEvents.isEmpty()) {
                for (OilEvent oilEvent : oilEvents) {
                    System.out.println("Almacenando Evento OIL: " + oilEvent.getTsAsString());
                    eventStore.storeOil(oilEvent);
                }
            }

            ArrayList<NewsEvent> newsEvents = eventHandler.handleNews();
            if (!newsEvents.isEmpty()) {
                for (NewsEvent newsEvent : newsEvents) {
                    System.out.println("Almacenando Evento NEWS: " + newsEvent.getPublishedAt() + " - " + newsEvent.getTitle());
                    eventStore.storeNews(newsEvent);
                }
            }

            try {
                Thread.sleep(1000); // Espera 1 segundo antes de revisar de nuevo
            } catch (InterruptedException e) {
                System.out.println("Interrumpido, finalizando...");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}
