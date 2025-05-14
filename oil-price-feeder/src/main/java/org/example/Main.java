package org.example;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        AlphaVantageProvider oilProvider = new AlphaVantageProvider(args[0]);
        /*
        SQLiteOilStore oilStore = new SQLiteOilStore();
        new Controller(oilProvider, oilStore).run();
        */
    }
}   