package subscriberHexagonal.application.controller;

import subscriberHexagonal.domain.port.in.EventHandler;
import subscriberHexagonal.domain.port.out.EventStore;
import java.util.List;

public class EventStoreController {
    private final EventHandler eventHandler;
    private final EventStore eventStore;

    public EventStoreController(EventHandler eventReceiver, EventStore eventStore){
        this.eventHandler = eventReceiver;
        this.eventStore = eventStore;
    }

    public void run(){
        eventHandler.start();
    }

}
