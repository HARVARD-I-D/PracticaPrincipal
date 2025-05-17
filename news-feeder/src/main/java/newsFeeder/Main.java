package newsFeeder;


import newsFeeder.application.controller.NewController;
import newsFeeder.infrastructure.adapters.api.NewsAPIProvider;
import newsFeeder.infrastructure.adapters.jms.NewEventFeeder;
import newsFeeder.infrastructure.port.NewProvider;

public class Main {
    public static void main(String[] args) {
        NewProvider newProvider = new NewsAPIProvider(args[0]);
        NewEventFeeder newEventFeeder = new NewEventFeeder();
        NewController newController = new NewController(newProvider, newEventFeeder);

        newController.run();

        try{
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        /*
        SQLiteNewStore newStore = new SQLiteNewStore();
        new NewController(newProvider, newStore).run();
        */
    }
}