package org.example;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        AlphaVantageProvider oilProvider = new AlphaVantageProvider(args[1]);
        ArrayList<OilPrice> prices = oilProvider.provide();
        SQLiteOilStore oilStore = new SQLiteOilStore();

        for (OilPrice price : prices) {
            oilStore.save(price);
        }
    }
}   