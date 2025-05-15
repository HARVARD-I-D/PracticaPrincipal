package oilHexagonal.adapter.in;

import oilHexagonal.adapter.out.api.AlphaVantageProvider;
import oilHexagonal.adapter.out.jms.OilEventFeeder;
import oilHexagonal.application.controller.Controller;


public class Main {
    public static void main(String[] args) {
        AlphaVantageProvider oilProvider = new AlphaVantageProvider(args[0]);
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