package subscriberHexagonal;


import subscriberHexagonal.adapter.in.jms.MultiEventReceiver;
import subscriberHexagonal.adapter.out.MultiEventStore;
import subscriberHexagonal.application.controller.EventStoreController;
import subscriberHexagonal.application.service.BrokerEventService;

public class Main {
    public static void main(String[] args){
        MultiEventReceiver receiver = new MultiEventReceiver();
        MultiEventStore store = new MultiEventStore();
        EventStoreController eventStore = new EventStoreController(receiver, store);

        eventStore.run();
    }
}
