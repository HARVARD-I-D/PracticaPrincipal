package eventStore.application.controller;

import eventStore.application.domain.model.OilEvent;
import eventStore.infrastructure.port.EventHandler;
import eventStore.infrastructure.port.EventStore;

import java.lang.reflect.Array;
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
                    System.out.println("Almacenando Evento: " + oilEvent.getTsAsString());
                    eventStore.storeOil(oilEvent);
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
