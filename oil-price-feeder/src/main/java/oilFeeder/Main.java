package oilFeeder;

import oilFeeder.infrastructure.adapters.api.AlphaVantageProvider;
import oilFeeder.infrastructure.adapters.jms.OilEventFeeder;
import oilFeeder.application.controller.Controller;

public class Main {
    public static void main(String[] args) {
        AlphaVantageProvider oilProvider = new AlphaVantageProvider(args[1]);
        OilEventFeeder oilEventFeeder = new OilEventFeeder();
        Controller controller = new Controller(oilProvider, oilEventFeeder);

        controller.run();

        try{
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        /*
        SQLiteOilStore oilStore = new SQLiteOilStore();
        new Controller(oilProvider, oilStore).run();
        */
    }
}