package oilHexagonal.adapter.in;

import oilHexagonal.adapter.out.api.AlphaVantageProvider;
import oilHexagonal.adapter.out.jms.OilEventFeeder;
import oilHexagonal.application.controller.Controller;


public class Main {
    public static void main(String[] args) {
        AlphaVantageProvider oilProvider = new AlphaVantageProvider(args[0]);
        /*
        SQLiteOilStore oilStore = new SQLiteOilStore();
        new Controller(oilProvider, oilStore).run();
        */
        OilEventFeeder oilEventFeeder = new OilEventFeeder();
        new Controller(oilProvider, oilEventFeeder).run();
    }
}