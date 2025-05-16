package subscriberHexagonal;


import subscriberHexagonal.adapter.in.jms.MultiEventHandler;
import subscriberHexagonal.adapter.out.MultiEventStore;
import subscriberHexagonal.application.controller.EventStoreController;

public class Main {
    public static void main(String[] args){
        MultiEventHandler handler = new MultiEventHandler();
        MultiEventStore store = new MultiEventStore();
        EventStoreController eventStore = new EventStoreController(handler, store);

        eventStore.run();
    }
}
