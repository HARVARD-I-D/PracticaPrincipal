package subscriberHexagonal.application.controller;

import subscriberHexagonal.adapter.in.jms.MultiEventReceiver;
import subscriberHexagonal.adapter.out.MultiEventStore;

import java.util.List;

public class EventStoreController {
    private final MultiEventReceiver eventReceiver;
    private final MultiEventStore eventStore;

    public EventStoreController(MultiEventReceiver eventReceiver, MultiEventStore eventStore){
        this.eventReceiver = eventReceiver;
        this.eventStore = eventStore;
    }

    public void run(){
        eventReceiver.start();
    }

}
