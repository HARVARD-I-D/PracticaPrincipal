package eventStore;

import eventStore.infrastructure.adapter.jms.MultiEventHandler;
import eventStore.infrastructure.adapter.store.MultiEventStore;
import eventStore.application.controller.EventStoreController;

public class Main {
    public static void main(String[] args){
        MultiEventHandler handler = new MultiEventHandler();
        MultiEventStore store = new MultiEventStore();
        EventStoreController eventController = new EventStoreController(handler, store);

        eventController.run();
    }
}
