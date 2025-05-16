package eventStore.application.controller;

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

    public void run(){
        eventHandler.start();
        try {
            Thread.sleep(5000); // Espera 5 segundos a que lleguen mensajes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<OilEvent> oilEvents = eventHandler.handleOil();
        for (OilEvent oilEvent : oilEvents){
            eventStore.storeOil(oilEvent);
        }
    }
}
